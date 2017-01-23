package me.kareluo.support.util.app;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;

import java.util.List;

import me.kareluo.support.util.value.ValueUtils;

/**
 * Created by felix on 17/1/18.
 */
public class ProcessUtils {

    private static final String TAG = "ProcessUtils";

    private ProcessUtils() {
        /* cannot be instantiated */
    }

    /**
     * 获取当前进程名称
     */
    public static String getCurrentProcessName(Context context) {
        RunningAppProcessInfo info = getCurrentProcessInfo(context);
        if (info != null) {
            return info.processName;
        }
        return null;
    }

    /**
     * 获取当前进程信息
     */
    public static RunningAppProcessInfo getCurrentProcessInfo(Context context) {
        int pid = Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        if (!ValueUtils.isEmpty(processes)) {
            for (RunningAppProcessInfo info : processes) {
                if (info.pid == pid) {
                    return info;
                }
            }
        }
        return null;
    }

    /**
     * 判断是否为主进程
     */
    public static boolean isMainProcess(Context context) {
        return ValueUtils.nonNullEquals(getCurrentProcessName(context), context.getPackageName());
    }
}
