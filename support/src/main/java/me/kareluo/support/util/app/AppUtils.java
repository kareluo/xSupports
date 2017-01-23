package me.kareluo.support.util.app;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by felix on 16/5/3.
 */
public class AppUtils {

    private AppUtils() {
        /* cannot be instantiated */
    }

    /**
     * 判断指定app是否安装
     *
     * @param context     上下文
     * @param packageName 包名
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0) != null;
        } catch (PackageManager.NameNotFoundException ignored) {

        }
        return false;
    }
}
