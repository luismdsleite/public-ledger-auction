package app;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import s_kademlia.utils.CryptoHash;

public class Test {
    public static byte[] stringToByte(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                                 + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    public static String byteToString(byte [] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = String.format("%02x", b);
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String hexString = "48656c6c6f20576f726c64";
        byte[] bytes = stringToByte(hexString);
        String after = byteToString(bytes);
        byte [] dd = stringToByte(after);
        String aftesr = byteToString(dd);

        System.out.println("Hex String: " + hexString);
        System.out.println("after2: " + after);
        
        System.out.println("after1: " + aftesr);
        
        String input = "boas";
        
        byte[] hashBytes = CryptoHash.toSha256(input.getBytes()).toByteArray();
        
        String str = new String(hashBytes, StandardCharsets.UTF_8);
    }
}
