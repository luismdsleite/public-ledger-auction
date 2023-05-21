package app;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import s_kademlia.utils.CryptoHash;

public class Test {
    
    public static void main(String[] args) {
        String originalString = "Hello, World!"; // Example string
        
        // Encoding the string to bytes
        byte[] encodedBytes = originalString.getBytes();
        System.out.println("Encoded Bytes: " + Arrays.toString(encodedBytes));

        // Decoding the bytes back to string
        String decodedString = new String(encodedBytes);
        System.out.println("Decoded String: " + decodedString);
    }
}

