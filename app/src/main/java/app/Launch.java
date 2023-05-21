package app;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import app.auction.Auction;
import app.auction.AuctionHandler;
import app.auction.Auctions;
import app.auction.Bid;
import app.clients.Client;
import app.utils.Utils;
import s_kademlia.KademliaNode;
import s_kademlia.LaunchNode;
import s_kademlia.node.Node;
import s_kademlia.utils.CryptoHash;


public class Launch {
    public static final Scanner scanner = new Scanner(System.in);
    //public static List<Transaction> trans = new ArrayList<>();
    public static List<Auction> auctions = new ArrayList<>();
    public static List<Bid> bids = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException, IOException, NumberFormatException, NoSuchAlgorithmException {
        if(args.length != 5) {
            System.out.println("Wrong command length. Provide the following format: <IP> <Port> <Bootstrap IP> <Bootstrap Port> <command>");
            System.exit(1);
        }

        switch (args[4]) {
            case "quit":
                scanner.close();
                return;
            case "miner":
                startMiner(args[0], Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]));
                break;
            case "auction":
                startAuction(args[0], Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]));
                break;
            case "client":
                startClient(args[0], Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]));
                break;
            case "help":
                help();
                break;
            default:
                System.out.println("Unknown command. Use 'help' for a list of available commands.");
                break;
        }
    }

    public static void help() {
        System.out.println("miner > mine block\nauction > create auction\nclient > bid in auctions");
    }

    public static void startMiner(String name, int port, String bootstrap, int bootstrapPort) throws InterruptedException, java.io.IOException, NoSuchAlgorithmException {
        
        System.out.println("I hate ");

    }

    public static void startAuction(String name, int port, String bootstrap, int bootstrapPort) throws InterruptedException, java.io.IOException, NoSuchAlgorithmException {
        KeyPair keys = CryptoHash.genKeyPair();
        
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    LaunchNode.launchNode(name, port, bootstrap, bootstrapPort, keys.getPublic(), keys.getPrivate());
                } catch (NoSuchAlgorithmException | IOException | InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });      
        thread.setDaemon(true); // Set the thread as a daemon thread 
        thread.start();
      

        //loading(kademliaNode);

        System.out.println("Create auction: <Seller> <Product> <Starting Price> <Duration>");
        
        String [] creatAuc = scanner.nextLine().split(" ");

        if(creatAuc.length != 4) {
            System.out.println("Wrong command length");
            return;
        }
        
        Node node = new Node(name, port);
        Auction auction = Auction.createAuction(creatAuc[0], creatAuc[1], Integer.parseInt(creatAuc[2]), Integer.parseInt(creatAuc[3]));
        auction.setKey();
        AuctionHandler newAuction = new  AuctionHandler(auction, node, keys);
        newAuction.start();
    }

    public static void startClient(String name, int port, String bootstrap, int bootstrapPort) throws InterruptedException, java.io.IOException, NoSuchAlgorithmException {        
        final KeyPair keys = CryptoHash.genKeyPair();
        
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    LaunchNode.launchNode(name, port, bootstrap, bootstrapPort, keys.getPublic(), keys.getPrivate());
                } catch (NoSuchAlgorithmException | IOException | InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });         
        thread.setDaemon(true); // Set the thread as a daemon thread 
        thread.start();
      

        //Utils.showAuctionsActive();
        //loading(kademliaNode);

        System.out.print("Client name: ");

        String client = scanner.nextLine();
        Node node = new Node(name, port);

        Client clientT = new Client(client, node);
        clientT.start();
    }
}
