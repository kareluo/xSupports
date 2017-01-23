package me.kareluo.support.util.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import me.kareluo.support.util.Logger;
import me.kareluo.support.util.value.StringUtils;
import me.kareluo.support.util.value.ValueUtils;

/**
 * Created by felix on 17/1/9.
 */
public class ZipUtils {

    private static final String TAG = "ZipUtils";

    private static final int BUFFER_SIZE = 8192;

    private static long CURRENT_MILLIS = System.currentTimeMillis();

    private ZipUtils() {
        /* cannot be instantiated */
    }

    /**
     * 将指定的一系列文件压缩到目标文件
     *
     * @param dstFile 压缩后的压缩文件
     * @param files   待压缩文件
     * @return 是否成功压缩
     */
    public static boolean zip(File dstFile, File... files) {
        return zip(dstFile, StringUtils.EMPTY, files);
    }

    /**
     * 将指定的一系列文件压缩到目标文件
     *
     * @param dstFile    压缩后的压缩文件
     * @param folderName 压缩根目录(可选)
     * @param files      待压缩文件
     * @return true/false 是否成功压缩
     */
    public static boolean zip(File dstFile, String folderName, File... files) {
        CURRENT_MILLIS = System.currentTimeMillis();
        if (!ValueUtils.isEmpty(files)) {
            FileOutputStream fos = null;
            ZipOutputStream zos = null;
            try {
                FileUtils.createIfNotExists(dstFile);
                fos = new FileOutputStream(dstFile);
                zos = new ZipOutputStream(fos);
                if (folderName != null) {
                    folderName = folderName.trim();
                }
                if (ValueUtils.isEmpty(folderName)) {
                    folderName = StringUtils.EMPTY;
                }
                for (File file : files) {
                    if (FileUtils.exists(file)) {
                        zipFiles(zos, folderName, file);
                    }
                }
                return true;
            } catch (IOException e) {
                Logger.w(TAG, e);
            } finally {
                FileUtils.safelyClose(zos);
                FileUtils.safelyClose(fos);
            }
        }
        return false;
    }

    private static void zipFiles(ZipOutputStream zos, String folderName, File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            folderName += file.getName() + File.separator;
            ZipEntry entry = new ZipEntry(folderName);
            entry.setTime(CURRENT_MILLIS);
            entry.setMethod(ZipEntry.DEFLATED);
            if (!ValueUtils.isEmpty(files)) {
                entry.setCrc(files.length);
                zos.putNextEntry(entry);
                zos.closeEntry();
                for (File fe : files) {
                    zipFiles(zos, folderName, fe);
                }
            } else {
                entry.setCrc(0);
                zos.putNextEntry(entry);
                zos.closeEntry();
            }
        } else if (file.isFile()) {
            ZipEntry entry = new ZipEntry(folderName + file.getName());
            entry.setSize(file.length());
            entry.setTime(CURRENT_MILLIS);
            zos.putNextEntry(entry);
            addFile(zos, file);
            zos.closeEntry();
        }
    }

    private static void addFile(ZipOutputStream zos, File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int len;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((len = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            Logger.w(TAG, e);
        } finally {
            FileUtils.safelyClose(fis);
        }
    }

    /**
     * 解压zip压缩文件到指定目录
     *
     * @return true/false 解压成功与否
     */
    public static boolean unzip(File zipFile, File dstDir) {
        if (FileUtils.exists(zipFile)) {
            FileUtils.createDirIfNotExists(dstDir);
            OutputStream os = null;
            InputStream is = null;
            try {
                int len;
                byte[] buffer = new byte[BUFFER_SIZE];
                ZipFile zf = new ZipFile(zipFile);
                Enumeration<? extends ZipEntry> entries = zf.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    File file = new File(dstDir, entry.getName());
                    if (entry.isDirectory()) {
                        FileUtils.createDirIfNotExists(file);
                    } else {
                        FileUtils.createIfNotExists(file);
                        os = new FileOutputStream(file);
                        is = zf.getInputStream(entry);
                        while ((len = is.read(buffer)) != -1) {
                            os.write(buffer, 0, len);
                        }
                        FileUtils.safelyClose(os);
                        FileUtils.safelyClose(is);
                    }
                }
                return true;
            } catch (IOException e) {
                Logger.w(TAG, e);
            } finally {
                FileUtils.safelyClose(os);
                FileUtils.safelyClose(is);
            }
        } else {
            Logger.i(TAG, "ZipFile is not exists : " + zipFile);
        }
        return false;
    }
}