package s_kademlia.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import generated.NodeAPI.NodeProto;
import s_kademlia.node.Node;

public class KademliaUtils {
    public static final int K = 20;
    public static final String CRYPTO_ALGO = "RSA";
    public static final String HASH_ALGO = "SHA-256";
    public static Node nodeProtoToNode(NodeProto n) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new Node(n.getPublicKey().toByteArray(), n.getIp(), n.getPort());
    }

    public static NodeProto nodeToNodeProto(Node n) {
        return NodeProto.newBuilder()
                .setIp(n.getName())
                .setPort(n.getPort())
                .setPublicKey(n.getNodeID().getPubKeyByteStr()).build();
    }
}
