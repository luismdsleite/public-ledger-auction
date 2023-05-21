package app.auction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

import app.utils.Utils;

public class Auctions {
    public static HashMap<String, Auctions> auctionS = new HashMap<>();
    Auction auction;
    List<Bid> bids = new ArrayList<>();
    public static List<Auction> auctions = new ArrayList<>();

    public Auctions(Auction auction) {
        this.auction = auction;
    }

    public Auction getAuction() {
        return auction;
    }

    public static List<Auction> getAuctionList() {
        return auctions;
    }

    public List<Bid> getBids() {
        return this.bids;
    }

    public Bid getLatestBid() {
        if(bids.isEmpty()){
            return null;
        }else{
            return Utils.highestBid(bids);
        }
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
        if(auctionS.containsKey(auction.getKey())) {
            System.out.println("Auction already active.");
            return false;
        }
        Auctions newAuction = new Auctions(auction);
        auctionS.put(auction.getKey(), newAuction);
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

        for (Entry<String, Auctions> entry : auctionS.entrySet()) {
            String key = entry.getKey();

            if(!new Date().before(getAuction(key).getEndDate())) {
                inactiveActions.add(key);
            }
        }

        for(String key : inactiveActions){
            auctionS.remove(key);
        }
    }

    public static List<Bid> getAuctionBids(String auctionKey) {
        Auctions a = auctionS.get(auctionKey);

        if(a == null)
            return null;

        return a.getBids();
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
    
    public void showAuctionState(){
        System.out.println("Auction : " + auction.getKey());
        Bid last = this.getLatestBid();
        if (last == null){
            System.out.println("No last bid");
        }
        else{
            System.out.println("Latest Bid: " + last.getAmount());
        }
        System.out.println();
    }

    public static void showAuctions() {
        for(Auctions auctions: auctionS.values()){
            auctions.showAuctionState();
        }
    }

}
