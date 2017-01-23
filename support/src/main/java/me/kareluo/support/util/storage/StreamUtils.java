package me.kareluo.support.util.storage;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;

import me.kareluo.support.util.Logger;
import me.kareluo.support.util.value.ValueUtils;

/**
 * Created by felix on 16/4/28.
 */
public class StreamUtils {

    private static final String TAG = "StreamUtils";

    private StreamUtils() {
        /* cannot be instantiated */
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Logger.w(TAG, e);
            }
        }
    }

    public static void close(Closeable... closeables) {
        if (!ValueUtils.isEmpty(closeables)) {
            for (Closeable closeable : closeables) {
                close(closeable);
            }
        }
    }

    public static void close(Collection<? extends Closeable> closeables) {
        if (!ValueUtils.isEmpty(closeables)) {
            for (Closeable closeable : closeables) {
                close(closeable);
            }
        }
    }

}
