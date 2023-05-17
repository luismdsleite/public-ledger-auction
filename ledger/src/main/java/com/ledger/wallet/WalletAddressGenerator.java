package com.ledger.wallet;

import java.util.Random;

public class WalletAddressGenerator {
    private static final String HEX_CHARS = "0123456789ABCDEF";
    private static final String PREFIX = "0x";
    private static final int ADDRESS_LENGTH = 42;

    public static String generateWalletAddress() {
        StringBuilder sb = new StringBuilder(PREFIX);

        Random random = new Random();
        for (int i = 0; i < ADDRESS_LENGTH - PREFIX.length(); i++) {
            char hexChar = HEX_CHARS.charAt(random.nextInt(HEX_CHARS.length()));
            sb.append(hexChar);
        }

        return sb.toString();
    }

    public static boolean verifyAddressFormat(String address) {
        return address.startsWith(PREFIX) && address.length() == ADDRESS_LENGTH
                && address.matches("^0x[0-9A-Fa-f]+$");
    }
}
