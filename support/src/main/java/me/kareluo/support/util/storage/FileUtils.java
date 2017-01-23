package me.kareluo.support.util.storage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import me.kareluo.support.util.Logger;

/**
 * Created by felix on 16/4/29.
 */
public class FileUtils {

    private static final String TAG = "FileUtils";

    private FileUtils() {
        /* cannot be instantiated */
    }

    private static final int BUFFER_SIZE = 8192;

    /**
     * 根据文件路径穿件一个文件
     *
     * @return true 创建了新文件，否则表示问题已存在。
     */
    public static boolean createIfNotExists(String path) throws IOException {
        return createIfNotExists(new File(path));
    }

    /**
     * 如果不存在就创建一个文件
     *
     * @return true 创建了新文件，否则表示问题已存在。
     */
    public static boolean createIfNotExists(File file) throws IOException {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            boolean mkdirs = false;
            if (!parentFile.exists()) {
                mkdirs = parentFile.mkdirs();
            }
            return mkdirs || file.createNewFile();
        }
        return false;
    }

    /**
     * @param dir 创建一个目录
     * @return true/false 是否创建,不表示成功失败
     */
    public static boolean createDirIfNotExists(File dir) {
        if (!dir.exists() || !dir.isDirectory()) {
            File parentFile = dir.getParentFile();
            boolean mkdirs = false;
            if (!parentFile.exists()) {
                mkdirs = parentFile.mkdirs();
            }
            return mkdirs || dir.mkdir();
        }
        return false;
    }

    /**
     * 关闭流等Closeable对象
     *
     * @param closeable 可关闭对象
     */
    public static void safelyClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Logger.w(TAG, e);
            }
        }
    }

    /**
     * 判断文件是否不为null且存在
     */
    public static boolean exists(File file) {
        return file != null && file.exists();
    }

    /**
     * 删除文件或文件夹
     */
    public static boolean delete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fe : files) {
                delete(fe);
            }
        }
        return file.delete();
    }

    /**
     * 拷贝文件
     */
    public static boolean copy(File fromFile, File toFile) {
        if (exists(fromFile)) {

            FileInputStream fis = null;
            BufferedInputStream bis = null;

            FileOutputStream fos = null;
            BufferedOutputStream bos = null;

            try {
                createIfNotExists(toFile);
                fis = new FileInputStream(fromFile);
                bis = new BufferedInputStream(fis);

                fos = new FileOutputStream(toFile);
                bos = new BufferedOutputStream(fos);

                int len;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((len = bis.read(buffer)) != -1) {
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
        }
        return false;
    }

}
