package com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.Utils.Prefs;

public class CameraService extends Service  {
    private static final String ID_FOREGROUND = "foreground";
    private NotificationManager notificationManager;
    private PhoneUnlockedReceiver receiver;


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i2) {

        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        startForeground(1000, getNotification(ID_FOREGROUND, "Background Service", NotificationHelper.Priority.LOW));
        this.receiver = new PhoneUnlockedReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        registerReceiver(this.receiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(this.receiver);
        } catch (Exception ignored) {
        }

    }

    private Notification getNotification(String str, String str2, NotificationHelper.Priority priority) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getPackageName() + "_" + str);
        builder.setOnlyAlertOnce(true);
        builder.setAutoCancel(true);
        builder.setPriority(priority.getBelow24());
        builder.setSmallIcon(R.mipmap.ic_launcher);


        if (Prefs.isBoot){
            builder.setContentTitle("App Restart Required");
            builder.setContentText("Please click to open the app");
        }else{
            builder.setContentTitle("Your device is safe.");
            builder.setContentText(String.format("Press to open %s. Long press to hide this notification.", getString(R.string.app_name)));
        }

        builder.setOngoing(true);
        builder.setShowWhen(false);
        builder.setGroup(ID_FOREGROUND);
        builder.setGroupSummary(false);

        if (Build.VERSION.SDK_INT >= 26) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(getPackageName() + "_" + str, str2, priority.getAboveAnd24());
            if (str.equals(ID_FOREGROUND)) {
                notificationChannel.setShowBadge(false);
            }
            NotificationManager notificationManager = this.notificationManager;
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
            builder.setChannelId(getPackageName() + "_" + str);
        }
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle(builder);

        if (Prefs.isBoot){
            inboxStyle.addLine("App Restart Required");
            inboxStyle.addLine("Please click to open the app");
        }else{
            inboxStyle.addLine(String.format("Press to open %s.", getString(R.string.app_name)));
            inboxStyle.addLine("Long press to hide this notification.");
        }


        return inboxStyle.build();
    }





    private localBinder binder = new localBinder();

    public class localBinder extends Binder {
    }




}