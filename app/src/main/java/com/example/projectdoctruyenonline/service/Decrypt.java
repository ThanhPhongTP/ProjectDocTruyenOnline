package com.example.projectdoctruyenonline.service;

import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Decrypt {

    public static String key = "ABCDEF123ERD456EABCDEF123ERD456E";

    public String encrypt(String data) throws Exception{
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        c.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] enVal = c.doFinal(data.getBytes());
        String encryptValue = Base64.encodeToString(enVal, Base64.DEFAULT);
        return encryptValue;
    }

    public static String decrypt(String outPut) throws Exception{
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decodedValue = Base64.decode(outPut, Base64.DEFAULT);
        byte[] decValue = cipher.doFinal(decodedValue);
        String deCryptValue = new String(decValue);
        return deCryptValue;

    }

//    public static SecretKeySpec generateKey(String s) throws Exception {
//        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        byte[] bytes = s.getBytes("UTF-8");
//        digest.update(bytes, 0 , bytes.length);
//        byte[] key = digest.digest();
//        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
//        return secretKeySpec;
//ABCDEF123ERD456EABCDEF123ERD456E
//    }
}
