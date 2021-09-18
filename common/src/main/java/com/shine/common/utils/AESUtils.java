package com.shine.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2021/5/14 16:52
 * fileName：AESUtils
 * Use：
 */
public class AESUtils {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String PRIVATE_KEY = "p6ltpjBktik7C5HH17kxbPR0DQJBqITk";
    private static final String MODE = "AES/CBC/PKCS5Padding";

    public static String encrypt(String sSrc) {
        try {
            Cipher cipher = Cipher.getInstance(MODE);
            int blockSize = cipher.getBlockSize();
            byte[] ivb = new byte[blockSize];
            RANDOM.nextBytes(ivb);
            IvParameterSpec iv = new IvParameterSpec(ivb);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8), "AES"), iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes());
            byte[] data = new byte[blockSize + encrypted.length];
            ByteBuffer buffer = ByteBuffer.wrap(data);
            buffer.put(ivb);
            buffer.put(encrypted);
            return Base64.encodeBase64String(data);
        } catch (Exception e) {
            return null;
        }
    }

    public static String decrypt(String sSrc) {
        try {
            Cipher cipher = Cipher.getInstance(MODE);
            int blockSize = cipher.getBlockSize();
            byte[] decodeBytes = Base64.decodeBase64(sSrc);
            int dataLength = decodeBytes.length - blockSize;
            byte[] ivb = new byte[blockSize];
            byte[] data = new byte[dataLength];
            ByteBuffer buffer = ByteBuffer.wrap(decodeBytes);
            buffer.get(ivb);
            buffer.get(data);
            IvParameterSpec iv = new IvParameterSpec(ivb);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8), "AES"), iv);
            byte[] original = cipher.doFinal(data);
            return new String(original);
        } catch (Exception ex) {
            return null;
        }
    }

//    public static void main(String[] args) {
//        String text = "hello world!!!";
//        String encoderStr = encrypt(text);
//        System.out.println("加密后：" + encoderStr);
//
//        String decrypt = decrypt(encoderStr);
//        System.out.println("解密后：" + decrypt);
//
//    }
}
