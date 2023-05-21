package app.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import app.auction.*;

public class Utils {
    public static String formatDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String string = calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "  " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
        return string ;
    }

    public static void printBidsHashMap(HashMap<String, Bid> hashMap) {
        for (Entry<String, Bid> entry : hashMap.entrySet()) {
            Bid value = entry.getValue();
            showBids(value);
        }
    }

    public static void showBids(Bid bid) {
        System.out.println("Key: " + bid.getKey());
        System.out.println("Buyer: " + bid.getBuyer() + "Value: " + bid.getAmount());
    }

    public static void printAuctionHashMap(HashMap<String, Auction> hashMap) {
        for (Entry<String, Auction> entry : hashMap.entrySet()) {
            Auction value = entry.getValue();
            showAuction(value);
        }
    }

    public static Bid highestBid(List<Bid> bids) {
        Bid b = null;
        int max = 0;
        for(Bid bid : bids) {
            if(bid.getAmount() > max) {
                max = bid.getAmount();
                b = bid;
            }
        }
        return b;
    }

    public static String [] parseGetAuction(byte [] response) {
        String data = new String(response);
        return data.split("_");
    }

    public static boolean checkBidEmptyGet(String [] data) {
        if(data.length == 6) {
            return false;
        }
        return true;
    }

    public static void showAuction(Auction auction) { 
        System.out.println("Key: " + auction.getKey());
        System.out.println("Product: " + auction.getProduct());
        System.out.println("Seller: " + auction.getSeller());
        System.out.println("Starting Price: " + auction.getStartPrice() + "€");
        System.out.println("Highest Bid Key: " + auction.getBestBid());
        System.out.println("Starting Date: " + formatDate(auction.getStartDate()));
        System.out.println("Ending Date: " + formatDate(auction.getEndDate()));
        System.out.println("Active: " + auction.isActive());
    }
}