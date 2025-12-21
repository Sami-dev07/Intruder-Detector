package com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC;

import android.app.ActivityManager;
import android.content.Context;

public class Utility {
    public static String imagePath = "";

    public static double latitude = 0.0;
    public static double longitude = 0.0;



    public static boolean isMyServiceRunning(Context context, Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
