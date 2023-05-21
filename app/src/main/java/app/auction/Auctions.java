package app.auction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import app.utils.Utils;

public class Auctions {
    public static HashMap<String, Auctions> auctionS = new HashMap<>();
    public static HashMap<String, Auction> auctionsData = new HashMap<>();

    Auction auction;
    List<Bid> bids = new ArrayList<>();

    public Auctions(Auction auction) {
        this.auction = auction;
    }

    public Auction getAuction() {
        return auction;
    }

    public List<Bid> getBids() {
        return this.bids;
    }

    public static synchronized void updateAucDatabase(Auction auction) {
        auctionsData.put(auction.getKey(), auction);
    }

    public boolean isUnique(Bid bid) {
        for(Bid b : bids) {
            if(b.getKey().equals(bid.getKey())) {
                return false;
            }
        }
        return true;
    }

    public static Auction getAuction(String key) {
        Auctions auctions = auctionS.get(key);
        if(auctions == null) {
            return null;
        }
        return auctions.getAuction();
    }

    public static List<Auction> getAuctions(){
        List<Auction> auctions = new ArrayList<>();

        for(Auctions auction : auctionS.values())
            auctions.add(auction.auction);

        return auctions;
    }

    public static boolean addAuction(Auction auction) {
        if(auctionsData.containsKey(auction.getKey())) {
            System.out.println("Auction already active.");
            return false;
        }

        auctionsData.put(auction.getKey(), auction);
        System.out.println("New auction added.");
        return true;
    }

    public static boolean checkBidAuction(Bid bid, Auction auction) {
        boolean verify = bid.getKey().equals(auction.getKey()) && bid.getSeller().equals(auction.getSeller());

        if(!verify) {
            System.out.println("Invalid bid.");
            return false;
        }

        if(!auctionS.get(bid.getKey()).isUnique(bid)) {
            System.out.println("Bid already exists.");
            return false;
        }

        return verify;
    }

    public static void updateActiveAuctions() {
        List<String> inactiveActions = new LinkedList<>();

        for (Entry<String, Auction> entry : auctionsData.entrySet()) {
            String key = entry.getKey();

            if(!new Date().before(getAuction(key).getEndDate())) {
                inactiveActions.add(key);
            }
        }

        for(String key : inactiveActions){
            auctionsData.remove(key);
        }
    }

    public static boolean updateBid(Bid bid) {
        updateActiveAuctions();
        if(auctionS.get(bid.getKey()) != null) { //se não existir
            if(!checkBidAuction(bid, auctionS.get(bid.getKey()).getAuction())) { //se não existe
                return false;
            }
            Auctions update = auctionS.get(bid.getKey());
            update.updateBids(bid);
        }
        return true;
    }

    public Bid updateBids(Bid bid){
        Bid prevBid = Utils.highestBid(bids); //melhor bid anterior
        bids.add(bid);
        return prevBid;
    }
    
    public static void showAuctionState(Auction auction){
        System.out.println("Auction : " + auction.getKey());
        Bid last = auction.getBestBid();
        if (last == null){
            System.out.println("No last bid");
        }
        else{
            System.out.println("Latest Bid: " + last.getAmount());
        }
        System.out.println();
    }

    public static void showAuctions() {
        for(Auction auction: auctionsData.values()){
            showAuctionState(auction);
        }
    }

}
