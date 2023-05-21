package app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static String byteToString(byte [] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = String.format("%02x", b);
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] stringToByte(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)+ Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    public static Date parseToDate(String string) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm:ss");
        Date date = null;
        try {
            // Parse the string to obtain the Date value
            date = dateFormat.parse(string);

            // Print the parsed Date value
            System.out.println("Parsed Date: " + date);
        } catch (ParseException e) {
            System.out.println("Invalid date format: " + string);
        }
        return date;
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
        if(data.length == 8) {
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
