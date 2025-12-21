package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    public static int attempts = 1;
    public static int whichActivity = 0;

    public static String browserURL = "";

    SharedPreferences sharedPreferences;

    public PrefManager (Context context){
        sharedPreferences = context.getSharedPreferences("intruder", Context.MODE_PRIVATE);
    }

    public void addToString(String name, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public String getStringExtra(String name){
        return sharedPreferences.getString(name, "a");
    }

    public void addToInteger(String name, int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name, value);
        editor.apply();
    }

    public int getIntegerExtra(String name){
        return sharedPreferences.getInt(name, 1);
    }

    public void addToBoolean(String name, boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    public boolean getBooleanExtra(String name){
        return sharedPreferences.getBoolean(name, false);
    }

}
