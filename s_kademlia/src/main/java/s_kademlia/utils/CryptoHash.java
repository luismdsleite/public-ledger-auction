package s_kademlia.utils;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class CryptoHash {
  /**
   * Hashes a byte array using SHA-256
   * 
   * @param bytes byte array to hash
   * @return the SHA-256 hash
   * @throws NoSuchAlgorithmException
   */
  public static BigInteger toSha256(byte[] bytes) throws NoSuchAlgorithmException {
    // Based on
    // https://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java
    MessageDigest md = MessageDigest.getInstance(KademliaUtils.HASH_ALGO);

    byte[] digest = md.digest(bytes);

    return new BigInteger(1, digest);
  }

  public static PublicKey bytesToPubKey(byte[] pubKeyBytes) throws InvalidKeySpecException, NoSuchAlgorithmException {

    // Create a key spec from the byte array
    X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(pubKeyBytes);

    // Get an instance of the RSA key factory
    KeyFactory keyFactory = KeyFactory.getInstance(KademliaUtils.CRYPTO_ALGO);

    // Generate the public key from the key spec
    return keyFactory.generatePublic(publicKeySpec);
  }

  public static KeyPair genKeyPair() throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KademliaUtils.CRYPTO_ALGO);
    keyPairGenerator.initialize(2048);

    while (true) {
      // Generate a key pair
      KeyPair kp = keyPairGenerator.generateKeyPair();
      var precedingZeros = KademliaUtils.getFirstSetBitIndex(toSha256(kp.getPublic().getEncoded()).toByteArray());
      if (precedingZeros >= KademliaUtils.STATIC_PUZZLE_ZEROS) {
        return kp;
      } else{
        System.out.println("Key pair generated with " + precedingZeros + " preceding zeros. Trying again.");
      }
    }

  }

}