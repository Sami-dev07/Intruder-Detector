package com.intruderselfie.geolocation.hiddenpic.thirdeye.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    SharedPreferences sharedPreferences;
    public static boolean isBoot = false;
    public static boolean isServiceRunning = false;
    public Prefs(Context context) {
        sharedPreferences = context.getSharedPreferences("intruder", Context.MODE_PRIVATE);
    }
    public void addPrefValues(String name, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }
    public boolean getPrefValue(String name) {
        return sharedPreferences.getBoolean(name, false);
    }
}
