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

    public Bid bet(String auctionKey, Auction auction) {
        System.out.print("#################################Auction key: " + auction.getKey());
        
        System.out.print("Amount to bet: ");
        String amount = scanner.nextLine();

        Bid bid = new Bid(auction.getKey(), "Thread (" + Thread.currentThread().getId() +")", Integer.parseInt(amount));

        if(!Auctions.updateBid(bid)){
            return null;
        }
        return bid;
    }

    @Override
    public void run() {
        System.out.println("Select auction key to bid.");
        Auctions.showAuctions();

        String auctionKey = scanner.nextLine();
        System.out.print("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$." + Auctions.auctionsData.size() + "AAAAAAAAA\nVALUEEEEEEEEEEEEEEEEEEEEEEEEEEEE" + Auctions.auctionsData.get(auctionKey));

        Auction auction = Auctions.auctionsData.get(auctionKey);

        if(auction == null) {
            System.out.print("Auction not found.");
            return;
        }

        Bid bid = bet(auctionKey, auction);

        Bid currentBest = auction.getBestBid(); 

        while(true) {
            if (bid == null){
                System.out.println("Bid is null (2)");
                return;
            }

            if (bid.getAmount() <= currentBest.getAmount()) {
                System.out.println("Another buyer has the highest bid (" + currentBest.getAmount()+ "€) in the auction "+ bid.getKey());
                System.out.print("\nDo u wish to bet?[y/n]");
                String response = scanner.nextLine();
                if(response.equals("y")){
                    continue;
                } else {
                    return;
                }
            } else {
                long timestamp = System.currentTimeMillis() / 1000L;
                KadStorageValue kadValue = new KadStorageValue(auction.getValue(), timestamp);
                KademliaClient.runPut(kademliaNode, Utils.stringToByte(auction.getKey()), kadValue);
                System.out.println("Bid successful");
            }

                bid = bet(auctionKey, auction);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
