package me.kareluo.support.util.value;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import me.kareluo.support.util.Logger;
import me.kareluo.support.util.storage.FileUtils;

/**
 * Created by felix on 16/5/3.
 */
public class HashUtils {

    private static final String TAG = "HashUtils";

    private static final char[] UPPER_CASE_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static final int HEX_LENGTH = 16;

    public static final int MASK_4BIT = 0x0F;

    private static final String MD5 = "MD5";

    private HashUtils() {
        /* cannot be instantiated */
    }

    /**
     * MD5值
     */
    public static byte[] md5(byte[] bytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(MD5);
            return messageDigest.digest(bytes);
        } catch (Exception e) {
            Logger.w(TAG, e);
        }
        return new byte[0];
    }

    /**
     * 计算文件的MD5值
     */
    public static byte[] md5File(File file) {
        if (FileUtils.exists(file) && file.isFile()) {
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int len;
                byte[] buffer = new byte[8192];
                MessageDigest digest = MessageDigest.getInstance(MD5);
                while ((len = bis.read(buffer)) != -1) {
                    digest.update(buffer, 0, len);
                }
                return digest.digest();
            } catch (IOException | NoSuchAlgorithmException e) {
                return new byte[0];
            } finally {
                FileUtils.safelyClose(bis);
                FileUtils.safelyClose(fis);
            }
        }
        return new byte[0];
    }

    /**
     * 返回一串数字和字母组成的文件MD5值的字符串
     */
    public static String md5FileString(File file) {
        byte[] bytes = md5File(file);
        if (bytes.length > 0) {
            return StringUtils.toString(bytes, Character.MAX_RADIX);
        }
        return null;
    }

    /**
     * 计算文件的MD5值的HEX串
     */
    public static String md5FileHexString(File file) {
        byte[] bytes = md5File(file);
        if (bytes.length > 0) {
            return hex(bytes);
        }
        return null;
    }

    /**
     * MD5的16进制字符串
     */
    public static String md5HexString(byte[] bytes) {
        byte[] md5s = md5(bytes);
        if (md5s.length > 0) {
            return hex(md5s);
        }
        return null;
    }

    /**
     * 将HEX字符串转为字节数组
     */
    public static byte[] antiHex(String text) {
        if ((text.length() & 1) != 0) {
            throw new IllegalArgumentException(
                    "argument is not a hex string, length=" + text.length());
        }
        String s = text.toUpperCase();
        char[] chars = s.toCharArray();
        byte[] bytes = new byte[chars.length >> 1];
        for (int i = 0; i < chars.length; i += 2) {
            bytes[i >> 1] = antiHex(new char[]{chars[i], chars[i | 1]});
        }
        return bytes;
    }

    public static byte antiHex(char[] chars) {
        if (chars.length < 2) {
            throw new IllegalArgumentException("array's length must big than 1.");
        }
        byte value = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < HEX_LENGTH; j++) {
                if (UPPER_CASE_DIGITS[j] == chars[i]) {
                    value = (byte) ((value << 4) | j);
                }
            }
        }
        return value;
    }

    /**
     * 转换成HEX字符串
     */
    public static String hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(hex(b));
        return sb.toString();
    }

    /**
     * 转换成HEX字符
     */
    public static char[] hex(byte b) {
        return new char[]{
                UPPER_CASE_DIGITS[(b >>> 4) & MASK_4BIT],
                UPPER_CASE_DIGITS[b & MASK_4BIT]
        };
    }
}
