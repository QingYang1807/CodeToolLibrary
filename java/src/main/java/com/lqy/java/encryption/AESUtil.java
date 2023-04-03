package com.lqy.java.encryption;

/**
 * AES加密工具类
 *
 * 首先定义了一个字节数组 byteArray，然后使用 String 构造方法将其转换为字符串。
 *
 * 注意，在将字节数组转换为字符串时，可以选择指定字符集。如果不指定字符集，Java 将使用默认字符集（通常为 UTF-8）进行转换。
 */
public class AESUtil {
    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static byte[] encrypt(String content, String password) {
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, getSecretKey(password));// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, getSecretKey(password));// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成加密秘钥
     *
     * @param password
     * @return
     */
    private static javax.crypto.SecretKey getSecretKey(final String password) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        javax.crypto.KeyGenerator kg = null;
        try {
            kg = javax.crypto.KeyGenerator.getInstance("AES");
            //AES 要求密钥长度为 128
            kg.init(128, new java.security.SecureRandom(password.getBytes()));
            //生成一个密钥
            javax.crypto.SecretKey secretKey = kg.generateKey();
            return secretKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String text = "123456";
        byte[] encrypt = encrypt(text, "000");
        System.out.println("加密后的数据: " + new String(encrypt));

        try {
            byte[] decrypt = decrypt(encrypt, "000");
            System.out.println("解密后的数据: " + new String(decrypt));
        } catch (Exception e) {
            System.out.println("密码错误，解密失败");
        }

    }
}
