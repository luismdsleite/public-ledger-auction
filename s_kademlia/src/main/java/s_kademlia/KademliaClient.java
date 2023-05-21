package s_kademlia;

import generated.nodeAPIGrpc;

import java.util.concurrent.TimeUnit;

import com.google.protobuf.ByteString;

import generated.NodeAPI.GetRequest;
import generated.NodeAPI.GetResponse;
import generated.NodeAPI.PutRequest;
import generated.NodeAPI.PutResponse;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import s_kademlia.node.Node;
import s_kademlia.storage.KadStorageValue;
import s_kademlia.utils.KademliaUtils;;

/**
 * Contains the calls a kademlia client executes to interact with the network.
 */
public class KademliaClient {
    /**
     * Execute a GET rpc call to a node.
     */
    private static KadStorageValue runGet(Node node, byte[] key)
            throws StatusRuntimeException {
        var stub = nodeAPIGrpc.newBlockingStub(ManagedChannelBuilder.forAddress(node.getName(), node.getPort())
                .usePlaintext()
                .build()).withDeadlineAfter(KademliaUtils.RPC_CALL_TIMEOUT, TimeUnit.MILLISECONDS);

        GetRequest getRequest = GetRequest.newBuilder()
                .setKey(ByteString.copyFrom(key))
                .build();

        GetResponse response = stub.get(getRequest);
        var storageEntry = new KadStorageValue(response.getValue().toByteArray(), response.getTimestamp());
        return storageEntry;
    }

    /**
     * Execute a PUT rpc call to a node.
     */
    private static boolean runPut(Node node, byte[] key, KadStorageValue value)
            throws StatusRuntimeException {
        var stub = nodeAPIGrpc.newBlockingStub(ManagedChannelBuilder.forAddress(node.getName(), node.getPort())
                .usePlaintext()
                .build()).withDeadlineAfter(KademliaUtils.RPC_CALL_TIMEOUT, TimeUnit.MILLISECONDS);

        PutRequest putRequest = PutRequest.newBuilder()
                .setKey(ByteString.copyFrom(key))
                .setValue(ByteString.copyFrom(value.getValueBytes()))
                .setTimestamp(value.getTimestamp())
                .build();
        
        PutResponse response = stub.put(putRequest);
        return response.getSuccess();
    }
}
