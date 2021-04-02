package com.hossain.business.common;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

public class Utils {

    public static final boolean isOk(String value) {
        return value != null && !value.isEmpty();
    }

    public static String encodePassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
}
