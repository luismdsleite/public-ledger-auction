package app.auction;

import java.util.Calendar;

import app.utils.Utils;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.*;
import s_kademlia.utils.CryptoHash;


public class Auction {
    public String auctionKey;
    public String product;
    public String seller;
    public int startPrice;
    public boolean state;
    public Date startDate;
    public Date endDate;
    public Bid bestBid;

    public Auction(String product, String seller, int startPrice, Date endDate) {
        this.product = product;
        this.seller = seller;
        this.startPrice = startPrice;
        this.state = true; // active
        this.startDate = new Date();
        this.endDate = endDate;
    }

    public String getKey() {
        return this.auctionKey;
    }

    public byte[] getValue() {
        String key = this.getProduct() + "_" + this.getSeller()+"_"+ this.getStartPrice() + "_" + Utils.formatDate(this.getStartDate()) + "_" + Utils.formatDate(this.getEndDate());
        if(this.bestBid != null) {
            key = key + "_" + bestBid.getSeller() + "_" + bestBid.getBuyer() + "_" + bestBid.getAmount();
        }
        return key.getBytes();
    }

    public void setKey() throws NoSuchAlgorithmException {
        String input = this.getSeller() + this.getProduct() + this.getStartPrice();
        byte[] hashBytes = CryptoHash.toSha256(input.getBytes()).toByteArray();

        String hash = Utils.byteToString(hashBytes);
        this.auctionKey = hash;
    }

    public String getProduct() {
        return this.product;
    }

    public String getSeller() {
        return this.seller;
    }

    public int getStartPrice() {
        return this.startPrice;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public Bid getBestBid() {
        return this.bestBid;
    }

    public void setBestBid(Bid bid) {
        this.bestBid = bid;
    }

    public boolean isActive() {
        if(new Date().after(this.endDate)) {
            setClosed();
            return false;
        }
        return this.state;
    }

    public void setClosed() {
        this.state = false;
    }

    public static Auction createAuction(String seller, String product, int startPrice, int timePeriod) {
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.MINUTE, timePeriod);
        Date endDate = c.getTime();
        Auction auction = new Auction(product, seller, startPrice, endDate);
        return auction;
    }
}
