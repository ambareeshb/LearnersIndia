package com.gbmainframe.learnersindia;

import com.gbmainframe.learnersindia.models.apiresponses.PaymentHashResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public static String hashCal(PaymentHashResponse response,String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }
}
