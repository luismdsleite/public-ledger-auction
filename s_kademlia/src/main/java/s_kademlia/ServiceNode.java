package s_kademlia;

import generated.NodeAPI.*;
import generated.nodeAPIGrpc.nodeAPIImplBase;
import io.grpc.stub.StreamObserver;
import s_kademlia.node.Node;

public class ServiceNode extends nodeAPIImplBase{
    private final transient Node localNode;
    
    public ServiceNode(Node localNode) {
        this.localNode = localNode;
    }

    @Override
    public void findNode(NodeInfo request, StreamObserver<NodesClose> responseObserver) {
        // TODO Auto-generated method stub
        super.findNode(request, responseObserver);
    }

    /**
     * Simple ping message. No additional info is given.
     */
    @Override
    public void ping(Empty request, StreamObserver<Empty> responseObserver) {
        System.out.println("Received a ping");
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
        // super.ping(request, responseObserver);
    }
}
