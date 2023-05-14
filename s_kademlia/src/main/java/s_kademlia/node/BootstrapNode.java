// package s_kademlia.node;

// import java.util.List;

// import generated.NodeAPI.NodeInfo;
// import generated.NodeAPI.NodesClose;
// import io.grpc.stub.StreamObserver;

// public class BootstrapNode extends Node {

//     public BootstrapNode(KademliaID nodeID, String name, int port) {
//         super(nodeID, name, port);
//     }

//     private List<Node> knownNodes;

//     @Override
//     public void findNode(NodeInfo request, StreamObserver<NodesClose> responseObserver) {
//         System.out.println("I am a bootstrap node!");
//         NodesClose.Builder resp = NodesClose.newBuilder();
        
//         // Do shit
//         for (Node n: knownNodes){
//             NodeInfo.Builder nodeInfo = NodeInfo.newBuilder();
//             nodeInfo
//                 .setIp(n.getName())
//                 .setPort(n.getPort());
//                 // .setPublicKey(n.getNodeID().getPubKeyInt()); 
//         }
//         // Add the response and send it.
//         responseObserver.onNext(resp.build());
//         responseObserver.onCompleted();
//     }

    
    
// }
