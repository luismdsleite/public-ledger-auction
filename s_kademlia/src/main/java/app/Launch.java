// package app;

// import java.io.IOException;
// import java.security.NoSuchAlgorithmException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Scanner;

// import com.ledger.transaction.Transaction;

// import app.auction.Auction;
// import app.auction.AuctionHandler;
// import app.auction.Auctions;
// import app.auction.Bid;
// import app.clients.Client;
// import s_kademlia.KademliaNode;

// public class Launch {
//     public static final Scanner scanner = new Scanner(System.in);
//     public static List<Transaction> mempool = new ArrayList<>();
//     public static List<Auction> auctions = new ArrayList<>();
//     public static List<Bid> bids = new ArrayList<>();

//     public static void main(String[] args) throws InterruptedException, IOException, NumberFormatException, NoSuchAlgorithmException {
//         if(args.length != 5) {
//             System.out.println("Wrong command length. Provide the following format: <IP> <Port> <Bootstrap IP> <Bootstrap Port>");
//             System.exit(1);
//         }

//         String command;
//         System.out.println("Enter a command: \n(Type 'help' for a list of available commands.)");

//         // Read commands from standard input in a loop
//         System.out.print("> ");
//         command = scanner.nextLine();
//         switch (command) {
//             case "quit":
//                 scanner.close();
//                 return;
//             case "miner":
//                 startMiner(args[1], Integer.parseInt(args[2]), args[3], Integer.parseInt(args[4]));
//                 break;
//             case "auction":
//                 startAuction(args[1], Integer.parseInt(args[2]), args[3], Integer.parseInt(args[4]));
//                 break;
//             case "client":
//                 startClient(args[1], Integer.parseInt(args[2]), args[3], Integer.parseInt(args[4]));
//                 break;
//             case "help":
//                 help();
//                 break;
//             default:
//                 System.out.println("Unknown command. Type 'help' for a list of available commands.");
//                 break;
//         }
//     }

//     public static void help() {
//         System.out.println("miner > mine block\nauction > create auction\nclient > bid in auctions");
//     }

//     public static void loading(KademliaNode kademliaNode) {
//         // auctions = kademliaNode.getAuctions();

//         // for (Auction auction : auctions) {
//         //     Auctions.addAuction(auction);

//         //     bids = kademliaNode.getBidsFromAuction(auction);
//         //     for (Bid bid : bids)
//         //         Auctions.updateBid(bid);
//         // }
//     }

//     public static void startMiner(String name, int port, String bootstrap, int bootstrapPort) throws InterruptedException, java.io.IOException, NoSuchAlgorithmException {
//         KademliaNode kademliaNode = new KademliaNode(name, port, bootstrap, bootstrapPort);
//         loading(kademliaNode);
        
//     }

//     public static void startAuction(String name, int port, String bootstrap, int bootstrapPort) throws InterruptedException, java.io.IOException, NoSuchAlgorithmException {
//         KademliaNode kademliaNode = new KademliaNode(name, port, bootstrap, bootstrapPort);
//         loading(kademliaNode);
//         System.out.println("Create auction: <Seller> <Product> <Starting Price> <Duration>");
        
//         String [] creatAuc = scanner.nextLine().split(" ");

//         if(creatAuc.length != 4) {
//             System.out.println("Wrong length");
//             return;
//         }

//         Auction auction = Auction.createAuction(creatAuc[1], creatAuc[2], Integer.parseInt(creatAuc[3]), Integer.parseInt(creatAuc[4]));
//         AuctionHandler newAuction = new  AuctionHandler(auction, kademliaNode);
//         newAuction.start();
//     }

//     public static void startClient(String name, int port, String bootstrap, int bootstrapPort) throws InterruptedException, java.io.IOException, NoSuchAlgorithmException {        
//         KademliaNode kademliaNode = new KademliaNode(name, port, bootstrap, bootstrapPort);
//         loading(kademliaNode);

//         System.out.print("Client name: ");

//         String client = scanner.nextLine();

//         Client clientT = new Client(client, kademliaNode);
//         clientT.start();
//     }
// }
