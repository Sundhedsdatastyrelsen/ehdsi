package dk.nsp.epps.service;

import lombok.SneakyThrows;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static String md5Hash(String input) {
        return md5Hash(input.getBytes());
    }
    //SneakyThrows is accepted, since the MD5 algorithm is assumed to always be present
    @SneakyThrows
    public static String md5Hash(byte[] input) {
        var hash = MessageDigest.getInstance("MD5").digest(input);
        return String.format("%032x", new BigInteger(1, hash));
    }
}
