package me.kareluo.support.util.value;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * Created by felix on 16/12/16.
 */
public class SecureUtils {

    /**
     * 迭代次数
     */
    public static final int ITERATION_COUNT = 100;

    private static final String ALGORITHM = "PBEWITHMD5andDES";

    private SecureUtils() {
        /* cannot be instantiated */
    }

    public static byte[] SALT = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};

    public static String encrypt(String data, String password) {
        try {
            return HashUtils.hex(encrypt(data.getBytes(), password, SALT));
        } catch (Exception e) {
            return data;
        }
    }

    public static String decrypt(String data, String password) {
        try {
            return new String(decrypt(HashUtils.antiHex(data), password, SALT));
        } catch (Exception e) {
            return data;
        }
    }

    public static byte[] encrypt(byte[] data, String password, byte[] salt) throws Exception {
        //转换密钥
        Key key = toKey(password);
        //实例化PBE参数材料
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
        //实例化
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        //初始化
        cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        //执行操作
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, String password, byte[] salt) throws Exception {
        //转换密钥
        Key key = toKey(password);
        //实例化PBE参数材料
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
        //实例化
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        //初始化
        cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        //执行操作
        return cipher.doFinal(data);
    }

    private static Key toKey(String password) throws Exception {
        //密钥彩礼转换
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        //实例化
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);

        return keyFactory.generateSecret(keySpec);
    }

    private static byte[] generateSalt() {
        return new SecureRandom().generateSeed(8);
    }
}
