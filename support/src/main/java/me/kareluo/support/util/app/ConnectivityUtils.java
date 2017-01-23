package me.kareluo.support.util.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by felix on 16/12/7.
 */
public class ConnectivityUtils {

    private ConnectivityUtils() {
        /* cannot be instantiated */
    }

    /**
     * 判断网络是否可用
     */
    public static boolean isAvailable(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isAvailable();
    }

    /**
     * 获取网络信息
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        return ((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }


}
