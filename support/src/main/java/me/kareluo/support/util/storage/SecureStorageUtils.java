package me.kareluo.support.util.storage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

import me.kareluo.support.util.Logger;

/**
 * Created by felix on 17/1/11.
 */
public class SecureStorageUtils {

    private static final String TAG = "SecureStorageUtils";

    private static final int BUFFER_SIZE = 8192;

    private SecureStorageUtils() {
        /* cannot be instantiated */
    }

    public static boolean random(File file, File secureFile, long key) {
        if (!FileUtils.exists(file)) {
            return false;
        }

        if (file.equals(secureFile)) {
            return random(file, key);
        }

        Random random = new Random(key);

        FileInputStream fis = null;
        BufferedInputStream bis = null;

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            FileUtils.createIfNotExists(secureFile);

            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);

            fos = new FileOutputStream(secureFile);
            bos = new BufferedOutputStream(fos);

            int len, i, j = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            byte[] bytes = new byte[BUFFER_SIZE];

            random.nextBytes(bytes);
            while ((len = bis.read(buffer)) != -1) {
                for (i = 0; i < len; i++) {
                    buffer[i] ^= bytes[j++];
                    if (j >= BUFFER_SIZE) {
                        j -= BUFFER_SIZE;
                        random.nextBytes(bytes);
                    }
                }
                bos.write(buffer, 0, len);
            }
            return true;
        } catch (IOException e) {
            Logger.w(TAG, e);
        } finally {
            FileUtils.safelyClose(bos);
            FileUtils.safelyClose(fos);
            FileUtils.safelyClose(fis);
            FileUtils.safelyClose(bis);
        }

        return false;
    }

    public static boolean random(File file, long key) {
        if (!FileUtils.exists(file)) {
            return false;
        }

        Random random = new Random(key);

        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rwd");

            int len, i, j = 0;
            long pos = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            byte[] bytes = new byte[BUFFER_SIZE];

            random.nextBytes(bytes);
            while ((len = raf.read(buffer)) != -1) {
                for (i = 0; i < len; i++) {
                    buffer[i] ^= bytes[j++];
                    if (j >= BUFFER_SIZE) {
                        j -= BUFFER_SIZE;
                        random.nextBytes(bytes);
                    }
                }
                raf.seek(pos);
                raf.write(buffer, 0, len);
                raf.seek(pos += len);
            }
            return true;
        } catch (IOException e) {
            Logger.w(TAG, e);
        } finally {
            FileUtils.safelyClose(raf);
        }

        return false;
    }

    public static void pi() {
        int b = 0;
        int a = 10000, c = 28000, d = 0, e = 0, g;
        int[] ans = new int[28001];
        int[] f = new int[28001];

        int num = 1;
        for (; b - c != 0; ) {
            f[b++] = a / 5;
        }

        for (; (g = c * 2) != 0; c -= 14) {
            d = 0;
            ans[num++] = (e + d / a);
            e = d % a;
            for (b = c; b - 1 != 0; d *= b) {
                d += f[b] * a;
                f[b] = d % --g;
                d /= g--;
                --b;
            }
        }

        for (int i = 0; i < 100; i++) {
            System.out.print(ans[i]);
        }
    }
}
