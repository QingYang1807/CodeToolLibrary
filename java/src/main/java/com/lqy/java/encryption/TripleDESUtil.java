package com.lqy.java.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 3DES加密解密工具,可以对字符串进行加密和解密操作 。
 * 在这个工具类中，有两个方法：encrypt 和 decrypt。
 * encrypt 方法将字符串数据和密钥作为参数，对数据进行加密并返回加密后的字符串。
 * decrypt 方法将加密后的字符串数据和密钥作为参数，对数据进行解密并返回解密后的字符串。
 *
 * 请注意，Triple DES 算法虽然比 DES 更安全，但仍然存在一定的安全风险。推荐使用更安全的加密算法，如 AES。
 */
public class TripleDESUtil {
    private static final String ALGORITHM = "DESede";

    public static String encrypt(String data, String key) throws Exception {
        DESedeKeySpec desKeySpec = new DESedeKeySpec(key.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decrypt(String encryptedData, String key) throws Exception {
        DESedeKeySpec desKeySpec = new DESedeKeySpec(key.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        String data = "Hello, World!";
        String key = "123456789012345678901234"; // 3DES 密钥长度为 24 个字节

        try {
            String encryptedData = encrypt(data, key);
            System.out.println("加密后的数据: " + encryptedData);

            String decryptedData = decrypt(encryptedData, key);
            System.out.println("解密后的数据: " + decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
