package s_kademlia.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import generated.NodeAPI.NodeProto;
import s_kademlia.node.Node;

public class KademliaUtils {
    public static final int K = 20;
    public static final String CRYPTO_ALGO = "RSA";
    public static final String HASH_ALGO = "SHA-256";
    public static final int RPC_CALL_TIMEOUT = 99999;
    public static final int MAX_RETRIES = 3;
    public static final int STATIC_PUZZLE_ZEROS = 2;
    public static Node nodeProtoToNode(NodeProto n) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new Node(n.getPublicKey().toByteArray(), n.getIp(), n.getPort());
    }

    public static NodeProto nodeToNodeProto(Node n) {
        return NodeProto.newBuilder()
                .setIp(n.getName())
                .setPort(n.getPort())
                .setPublicKey(n.getNodeID().getPubKeyByteStr()).build();
    }

    /**
     * Counts the number of leading 0's in this NodeId
     *
     * @return Integer The number of leading 0's
     */
    public static int getFirstSetBitIndex(byte[] hashBytes) {
        int prefixLength = 0;

        for (byte b : hashBytes) {
            if (b == 0) {
                prefixLength += 8;
            } else {
                /* If the byte is not 0, we need to count how many MSBs are 0 */
                int count = 0;
                for (int i = 7; i >= 0; i--) {
                    boolean a = (b & (1 << i)) == 0;
                    if (a) {
                        count++;
                    } else {
                        break; // Reset the count if we encounter a non-zero number
                    }
                }

                /* Add the count of MSB 0s to the prefix length */
                prefixLength += count;

                /* Break here since we've now covered the MSB 0s */
                break;
            }
        }
        return prefixLength;
    }
}
