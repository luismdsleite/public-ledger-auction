package app.auction;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import app.utils.Utils;
import s_kademlia.utils.CryptoHash;

public class Bid {
    public String auctionKey;
    public String buyer;
    public String seller;
    public int amount;
    public Date bidDate;
    public float fee;

    public Bid(String auctionKey, String buyer, int amount) {
        this.auctionKey = auctionKey;
        this.buyer = buyer;
        this.amount = amount;
        this.bidDate = new Date();
    }

    public byte[] getKeyHash() {
        try {
            return CryptoHash.toSha256(this.getKey().getBytes()).toByteArray();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getValue() {
        String key = this.getAmount() + "_" + Utils.formatDate(this.getBidDate()) + "_" + this.getBuyer();
        return key.getBytes();
    }

    public void setKey() throws NoSuchAlgorithmException {
        String key = this.getAmount() + Utils.formatDate(this.getBidDate()) + this.getBuyer();
        this.auctionKey = key;
    }

    public float getFee() {
        return this.fee;
    }

    public String getKey() {
        return this.auctionKey;
    }

    public String getBuyer() {
        return this.buyer;
    }

    public String getSeller() {
        return this.seller;
    }

    public int getAmount() {
        return this.amount;
    }

    public Date getBidDate() {
        return this.bidDate;
    }

    public static Bid createBid(String auctionKey, String buyer, int amount) {
        Bid bid = new Bid(auctionKey, buyer, amount);
        return bid;
    }
}
