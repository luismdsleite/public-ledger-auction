package app.clients;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import static java.lang.Thread.sleep;


import app.auction.*;
import s_kademlia.KademliaNode;

public class Client extends Thread {
    public final Scanner scanner =new Scanner(System.in);
    public KademliaNode kademliaNode;
    public String name;
    
    public Client(String name, KademliaNode kademliaNode) {
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
        Auction auction = Auctions.getAuction(auctionKey);

        if(auction == null){
            System.out.println("Auction not found");
            return null;
        }
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
        System.out.println("Select auction to bid.");
        Auctions.showAuctions();

        String auctionKey = scanner.nextLine();
        
        Bid bid = bet(auctionKey);

        if (bid == null){
            System.out.println("Bid is null (1)");
            return;
        }
        
        // Store bid no Kademlia
        // if(){
        //     return null;
        // }

        List<Bid> bids = Auctions.getAuctionBids(bid.getKey());

        while(true) {
            if (bid == null){
                System.out.println("Bid is null (2)");
                return;
            }

            if (Auctions.auctionS.get(bid.getKey()) != null){
                System.out.println("Auction is closed");
                return;
            }
            
            if(bids == null) {
                System.out.println("Bids is null");
                return;
            }

            Bid bestBid = bids.get(bids.size() - 1);

            if (bid != bestBid) {
                System.out.println("Another buyer has the highest bid (" + bestBid.getAmount()+ "€) in the auction "+ bid.getKey() + "\nDo u wish to bet?[y/n]");
                String response = scanner.nextLine();
                if(response.equals("y")){
                    bid = bet(auctionKey);
                    if(bid != null){
                        System.out.println("Bid is null (3)");
                    } else {
                        System.out.println("Bid successful");
                    }
                } else {
                    return;
                }
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
