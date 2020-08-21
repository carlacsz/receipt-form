package com.company.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class SimpleMd5 {
    public static final Logger LOGGER = Logger.getLogger(SimpleMd5.class.getName());

    public static String getHash(String message) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(message.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuilder encodeMessage = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) { // Convert it to hexadecimal format
                encodeMessage.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return encodeMessage.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warning("Error while getting hash: " + e.toString());
            throw e;
        }
    }
}
