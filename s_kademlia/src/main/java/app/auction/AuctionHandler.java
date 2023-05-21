package app.auction;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ledger.security.RSAKeyGenerator;
import com.ledger.transaction.Transaction;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import s_kademlia.KademliaNode;

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

        kademliaNode.put(auction.getKeyHash(), auction.getValue());

        runningAuction = new Thread(this, "Auction Running: " + auction.getKey());
        runningAuction.start();
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
        while(this.bids.isEmpty()){
            this.bids = Auctions.getAuctionBids(auction.getKey());
            try {
                sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int lastBid = this.bids.size() - 1;
        Bid bestBid = this.bids.get(lastBid);
        while(new Date().before(this.auction.getEndDate())) {
            lastBid = this.bids.size() - 1;

            if (this.bids.get(lastBid) != bestBid){
                bestBid = this.bids.get(lastBid);
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Auction closed");
        PrivateKey privateKey = this.kademliaNode.getPvtKey();
        PublicKey publicKey = this.kademliaNode.getPubKey();

        Transaction transaction = new Transaction(bestBid.getBuyer(), bestBid.getSeller(), RSAKeyGenerator.getPublicKey(publicKey), (float) bestBid.getAmount(), bestBid.getFee());
        RSAKeyGenerator.signString(transaction.getHash() , RSAKeyGenerator.getPrivateKey(privateKey));
    }
}
