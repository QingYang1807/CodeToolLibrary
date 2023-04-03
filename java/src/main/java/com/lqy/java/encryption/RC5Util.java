package com.lqy.java.encryption;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;

/**
 * RC5加密解密工具,可以对字符串进行加密和解密操作 。
 * 在这个工具类中，有两个方法：encrypt 和 decrypt。
 * encrypt 方法将字符串数据和密钥作为参数，对数据进行加密并返回加密后的字符串。
 * decrypt 方法将加密后的字符串数据和密钥作为参数，对数据进行解密并返回解密后的字符串。
 * <p>
 * 请注意，RC4 算法已经被认为是不安全的。推荐使用更安全的加密算法，如 AES。
 * <p>
 * 由于 Java 默认的加密提供程序不包含 RC5 算法，需要使用第三方库，如 Bouncy Castle。
 */

public class RC5Util {
    private static final String ALGORITHM = "RC5";
    private static final String TRANSFORMATION = "RC5/CBC/PKCS7Padding";
    private static final byte[] IV = new byte[8]; // 初始化向量 (IV) 的字节数组，长度为 8 字节

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String encrypt(String data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decrypt(String encryptedData, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION, "BC");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        String data = "Hello, World!";
        String key = "12345678"; // RC5 密钥长度可以是任意长度，但推荐至少为 8 个字节

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
