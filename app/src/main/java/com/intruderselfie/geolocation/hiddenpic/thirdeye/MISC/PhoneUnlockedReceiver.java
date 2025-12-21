package com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

import com.intruderselfie.geolocation.hiddenpic.thirdeye.Utils.Prefs;

public class PhoneUnlockedReceiver extends BroadcastReceiver {
    Prefs prefs;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        prefs = new Prefs(context);
//        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
//            if (prefs.getPrefValue("status")) {
//                Prefs.isBoot = true;
//                Prefs.isServiceRunning = true;
//                startBackgroundService(context);
//            }
//        }
    }
    public void startBackgroundService(Context context) {
        if (!Utility.isMyServiceRunning(context, CameraService.class)) {
            ContextCompat.startForegroundService(context, new Intent(context, CameraService.class));
        }
    }


}
