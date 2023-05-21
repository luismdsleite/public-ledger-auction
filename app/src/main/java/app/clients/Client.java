package app.clients;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util;

import static java.lang.Thread.sleep;

import java.security.NoSuchAlgorithmException;

import app.auction.*;
import app.utils.Utils;
import s_kademlia.KademliaClient;
import s_kademlia.node.Node;
import s_kademlia.storage.KadStorageValue;
import s_kademlia.utils.CryptoHash;


public class Client extends Thread {
    public final Scanner scanner =new Scanner(System.in);
    public Node kademliaNode;
    public String name;
    
    public Client(String name, Node kademliaNode) {
        this.name = name;
        this.kademliaNode = kademliaNode;
    }

    @Override
    public String toString() {
        return "Buyer: " + this.name;
    }

    public String getBuyer() {
        return this.name;
    }

    public Bid bet(String auctionKey) {

        System.out.print("Amount to bet: ");
        String amount = scanner.nextLine();

        Bid bid = new Bid(auctionKey, this.name, Integer.parseInt(amount));

        return bid;
    }

    public Auction updateAuction(String auctionKey) {
        KadStorageValue kadValue1 = KademliaClient.runGet(kademliaNode, Utils.stringToByte(auctionKey));

        if(kadValue1 == null) {
            System.out.println("Auction doesn't exist.");
            return null;
        }

        String bidArgs [] = Utils.parseGetAuction(kadValue1.getValueBytes());

        Bid currentBest = null;

        if(!Utils.checkBidEmptyGet(bidArgs)) {
            currentBest = new Bid(auctionKey, this.name, Integer.parseInt(bidArgs[7]));
        }

        Auction auction = new Auction(bidArgs[0], bidArgs[1], Integer.parseInt(bidArgs[2]), Utils.parseToDate(bidArgs[3]));
        try {
            auction.setKey();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Erro setKey()");
        }

        if(currentBest != null) {
            auction.setBestBid(currentBest);
        }

        return auction;
    }

    @Override
    public void run() {
        System.out.println("Select auction key to bid.");
       
        String auctionKey = scanner.nextLine();

        Auction auction = updateAuction(auctionKey);
        Utils.showAuction(auction);

        Bid bid = bet(auctionKey);

        while(true) {
            if (bid == null){
                System.out.println("Bid is null (2)");
                break;
            }

            if(!auction.state) {
                System.out.println("Auction ended.");

            }

            if(auction.getBestBid() == null || auction.getStartPrice() < bid.getAmount()) { // não ha best bid 
                long timestamp = System.currentTimeMillis() / 1000L;
                KadStorageValue kadValue = new KadStorageValue(auction.getValue(), timestamp);
                KademliaClient.runPut(kademliaNode, Utils.stringToByte(auctionKey), kadValue);
                System.out.println("Bid successful (1)");
                auction.setBestBid(bid);
            }else {
                if(auction.getStartPrice() >= bid.getAmount()) {
                    System.out.print("You must provide a amount higher than "+auction.getStartPrice()+"€\nDo u wish to bet? [y/n] ");
                    String response = scanner.nextLine();
                    if(!response.equals("y")){
                        break;
                    }
                } else if (bid.getAmount() <= auction.getBestBid().getAmount()) {
                    System.out.println("Another buyer has the highest bid (" + auction.getBestBid().getAmount()+ "€) in the auction "+ bid.getKey());
                    System.out.print("\nDo u wish to bet? [y/n]");
                    String response = scanner.nextLine();
                    if(!response.equals("y")){
                        break;
                    }

                } else {
                    long timestamp = System.currentTimeMillis() / 1000L;
                    KadStorageValue kadValue = new KadStorageValue(auction.getValue(), timestamp);
                    KademliaClient.runPut(kademliaNode, Utils.stringToByte(auctionKey), kadValue);
                    System.out.println("Bid successful (2)");
                    auction.setBestBid(bid);
                }
            } 
                auction = updateAuction(auctionKey);
                Utils.showAuction(auction);
                bid = bet(auctionKey);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
