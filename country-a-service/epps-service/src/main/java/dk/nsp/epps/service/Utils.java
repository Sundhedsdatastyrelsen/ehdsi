package dk.nsp.epps.service;

import lombok.SneakyThrows;

import java.security.MessageDigest;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class Utils {
        //SneakyThrows is accepted, since the MD5 algorithm is assumed to always be present
    @SneakyThrows
    public static String Md5Hash(String input) {
        byte[] bytes = input.getBytes();
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] hash = digest.digest(bytes);
        return new String(hash);
    }
}
