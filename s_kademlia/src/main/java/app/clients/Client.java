// package app.clients;

// import java.util.HashMap;
// import java.util.List;
// import java.util.Scanner;
// import static java.lang.Thread.sleep;

// import java.security.NoSuchAlgorithmException;

// import app.auction.*;
// import app.utils.Utils;
// import s_kademlia.KademliaNode;
// import s_kademlia.storage.KadStorageValue;
// import s_kademlia.utils.CryptoHash;

// public class Client extends Thread {
//     public final Scanner scanner =new Scanner(System.in);
//     public KademliaNode kademliaNode;
//     public String name;
    
//     public Client(String name, KademliaNode kademliaNode) {
//         this.name = name;
//         this.kademliaNode = kademliaNode;
//     }

//     @Override
//     public String toString() {
//         return "Buyer: " + this.name;
//     }

//     public String getBuyer() {
//         return this.name;
//     }

//     public Bid bet(String auctionKey) {
//         Auction auction = Auctions.getAuction(auctionKey);

//         if(auction == null){
//             System.out.println("Auction not found");
//             return null;
//         }

//         System.out.print("Amount to bet: ");
//         String amount = scanner.nextLine();

//         Bid bid = new Bid(auction.getKey(), "Thread (" + Thread.currentThread().getId() +")", Integer.parseInt(amount));

//         if(!Auctions.updateBid(bid)){
//             return null;
//         }
//         return bid;
//     }

//     public byte[] auctionKeytoBytes(String key) {
//         byte[] keyByte;
//         try {
//             keyByte = CryptoHash.toSha256(key.getBytes()).toByteArray();
//         } catch (NoSuchAlgorithmException e) {
//             e.printStackTrace();
//             return null;
//         }
//         return keyByte;
//     }

//     public Bid lastBestBid(String auctionKey) {
//         byte [] by = auctionKeytoBytes(auctionKey);
//         if(by == null) {
//             System.out.println("Key error (1)");
//         }

//         byte [] auctionBytes = kademliaNode.get(by).getValueBytes();
        
//         String bidArgs [] = Utils.parseGetAuction(auctionBytes);

//         // formato product_startPri_startDate_endDate_buyer_ammount

//         if(Utils.checkBidEmptyGet(bidArgs)) { //se não exists
//             return null;
//         }

//         Bid bid = new Bid(auctionKey, bidArgs[3], Integer.parseInt(bidArgs[4]));
//         return bid;
//     }

//     @Override
//     public void run() {
//         System.out.println("Select auction key to bid.");
//         Auctions.showAuctions();

//         String auctionKey = scanner.nextLine();
        
//         Bid bid = bet(auctionKey);

//         if (bid == null){
//             System.out.println("Bid is null (1)");
//             return;
//         }

//         Bid currentBest = lastBestBid(auctionKey); // retorna byte array 

//         while(true) {
//             if (bid == null){
//                 System.out.println("Bid is null (2)");
//                 return;
//             }

//             if (bid.getAmount() < currentBest.getAmount()) {
//                 System.out.println("Another buyer has the highest bid (" + currentBest.getAmount()+ "€) in the auction "+ bid.getKey() + "\nDo u wish to bet?[y/n]");
//                 String response = scanner.nextLine();
//                 if(response.equals("y")){
//                     bid = bet(auctionKey);
//                     if(bid == null){
//                         System.out.println("Bid is null (3)");
//                     } else {

//                         long timestamp = System.currentTimeMillis() / 1000L;
//                         KadStorageValue kadValue = new KadStorageValue(auctionKey, timestamp);
//                         byte [] temp = auctionKeytoBytes(auctionKey);

//                         if(temp == null) {
//                             System.out.println("Key error (2)");
//                         }

//                         kademliaNode.put(temp, kadValue);
//                         System.out.println("Bid successful");
//                     }
//                 } else {
//                     return;
//                 }
//             }

//             try {
//                 sleep(1000);
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//         }
//     }
// }
