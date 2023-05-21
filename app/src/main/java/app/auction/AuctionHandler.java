package app.auction;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;

import org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util;

import ledger.security.RSASignatureUtils;
import ledger.transaction.Transaction;

import app.utils.Utils;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import s_kademlia.KademliaNode;
import s_kademlia.storage.KadStorageValue;

public class AuctionHandler extends Thread {
    private Auction auction;
    private List<Bid> bids;
    private Thread runningAuction;
    public KademliaNode kademliaNode;
    
    public AuctionHandler(Auction auction, KademliaNode kademliaNode) {
        this.auction = auction;
        this.kademliaNode = kademliaNode;
        Auctions.addAuction(auction);
        this.bids = Auctions.getAuctionBids(auction.getKey());
        //Create Auction and store it

        long timestamp = System.currentTimeMillis() / 1000L;
        KadStorageValue kadValue = new KadStorageValue(auction.getValue(), timestamp);
        
        kademliaNode.put(auction.getKeyHash(), kadValue);
        
        Utils.showAuction(auction);

        runningAuction = new Thread(this, "Auction Running: " + auction.getKey());
        runningAuction.start();
    }

    public Bid updateAuctionBest(String auctionKey) {
        byte [] auctionBytes = kademliaNode.get(auction.getKeyHash()).getValueBytes();
        
        String bidArgs [] = Utils.parseGetAuction(auctionBytes);

        // formato product_startPri_startDate_endDate_buyer_ammount

        if(Utils.checkBidEmptyGet(bidArgs)) { //se não exists
            return null;
        }

        Bid bid = new Bid(auction.getKey(), bidArgs[3], Integer.parseInt(bidArgs[4]));

        auction.setBestBid(bid);
        this.bids.add(bid);
        return bid;
    }

    public String signString(String data, String privateKey) {
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKeyObj = keyFactory.generatePrivate(keySpec);

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKeyObj);
            signature.update(data.getBytes());

            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void run() {
        System.out.println("Auction started");
        Bid lastBest = updateAuctionBest(auction.getKey()); //verifica se há alguma bid
        Bid currentBest = lastBest;
        
        while(new Date().before(this.auction.getEndDate())) {
            lastBest = updateAuctionBest(auction.getKey());
            if (lastBest != currentBest){
                currentBest = lastBest;
                auction.setBestBid(currentBest);
                // DAR STORE DA MELHOR BID QUANDO ELA MUDA??
                long timestamp = System.currentTimeMillis() / 1000L;
                KadStorageValue kadValue = new KadStorageValue(auction.getValue(), timestamp);

                kademliaNode.put(auction.getKeyHash(), kadValue);
                
                Utils.showAuction(auction);
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Auction closed");
        PrivateKey privateKey = this.kademliaNode.getPvtKey();
        PublicKey publicKey = this.kademliaNode.getPubKey();

        Transaction transaction = new Transaction(currentBest.getBuyer(), currentBest.getSeller(), publicKey, (float) currentBest.getAmount(), currentBest.getFee());
        RSASignatureUtils.signString(transaction.getHash() , privateKey);
    }
}
