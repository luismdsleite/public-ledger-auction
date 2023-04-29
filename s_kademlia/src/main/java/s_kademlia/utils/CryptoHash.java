package s_kademlia.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    MessageDigest md = MessageDigest.getInstance("SHA-256");

    md.update(bytes);
    byte[] digest = md.digest();

    return new BigInteger(1, digest);
  }
}