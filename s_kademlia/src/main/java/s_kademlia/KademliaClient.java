package s_kademlia;

import generated.nodeAPIGrpc;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
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
        public static KadStorageValue runGet(Node node, byte[] key)
                        throws StatusRuntimeException {
                var channel = ManagedChannelBuilder.forAddress(node.getName(), node.getPort())
                                .usePlaintext()
                                .build();
                var stub = nodeAPIGrpc.newBlockingStub(channel).withDeadlineAfter(KademliaUtils.RPC_CALL_TIMEOUT,
                                TimeUnit.MILLISECONDS);

                GetRequest getRequest = GetRequest.newBuilder()
                                .setKey(ByteString.copyFrom(key))
                                .build();

                GetResponse response = stub.get(getRequest);
                var storageEntry = new KadStorageValue(response.getValue().toByteArray(), response.getTimestamp());
                channel.shutdown();
                return storageEntry;
        }

        /**
         * Execute a PUT rpc call to a node.
         */
        public static boolean runPut(Node node, byte[] key, KadStorageValue value)
                        throws StatusRuntimeException {
                var channel = ManagedChannelBuilder.forAddress(node.getName(), node.getPort())
                                .usePlaintext()
                                .build();
                var stub = nodeAPIGrpc.newBlockingStub(channel).withDeadlineAfter(KademliaUtils.RPC_CALL_TIMEOUT,
                                TimeUnit.MILLISECONDS);

                PutRequest putRequest = PutRequest.newBuilder()
                                .setKey(ByteString.copyFrom(key))
                                .setValue(ByteString.copyFrom(value.getValueBytes()))
                                .setTimestamp(value.getTimestamp())
                                .build();

                PutResponse response = stub.put(putRequest);
                channel.shutdown();
                return response.getSuccess();
        }

        public static void main(String[] args)
                        throws NumberFormatException, StatusRuntimeException, NoSuchAlgorithmException, UnsupportedEncodingException {

                // // Generate a random byte array
                // Random random = new Random(17);
                // byte[] key = new byte[32];
                // random.nextBytes(key);
                
                // var entry = new KadStorageValue("Lightweight, LIGHTWEIGHT BABYYYYYY".getBytes(), 12323);
                
                // runPut(new Node(args[0], Integer.parseInt(args[1])), key, entry);
                
                // System.out.println("---------------------------------");
                
                // var recEntry = runGet(new Node(args[0], Integer.parseInt(args[1])), key);

                // System.out.println(new String(recEntry.getValueBytes(), "UTF-8"));

                // System.out.println("FINITO");
        }
}
