package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.IntruderSelfieActivities;

import android.Manifest;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.NativeAdView;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC.CameraService;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC.PasswordReceiver;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC.Utility;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.Utils.Prefs;

import java.util.List;

public class SelfieModeActivate extends AppCompatActivity {
    Prefs prefs;
    TextView activateText;
    ImageView activate_service_ui, selfie_off_ui;
    private static final int REQUEST_CODE_PERMISSIONS = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie_mode_activate);
        prefs = new Prefs(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.tap_animation);
        activateText = findViewById(R.id.activateText);
        selfie_off_ui = findViewById(R.id.selfie_off_ui);

        NativeAdView nativeAdView = new NativeAdView();
        nativeAdView.loadNativeGreen(this);

        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(this, PasswordReceiver.class);

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                if (prefs.getPrefValue("status")) {
                    prefs.addPrefValues("status", false);
                    selfie_off_ui.setImageResource(R.drawable.selfie_off_ui);
                    activateText.setText("Deactivated");
                    activate_service_ui.setImageResource(R.drawable.activate_service_ui);
                    activateText.setTextColor(getColor(R.color.red));
                    stopBackgroundService();
                } else {
                    prefs.addPrefValues("status", true);
                    activateText.setText("Activated");
                    activate_service_ui.setImageResource(R.drawable.activate_service_on_ui);
                    activateText.setTextColor(getColor(R.color.green));
                    selfie_off_ui.setImageResource(R.drawable.selfie_on_ui);

                    startBackgroundService();
                }

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
            }


        };

        activate_service_ui = findViewById(R.id.activate_service_ui);
        activate_service_ui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activate_service_ui.startAnimation(animation);
                boolean isAdminActive = devicePolicyManager.isAdminActive(componentName);

           if (isAdminActive){

               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {



                       TedPermission.create()
                               .setPermissionListener(permissionlistener)
                               .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                               .setPermissions(Manifest.permission.CAMERA, Manifest.permission.POST_NOTIFICATIONS)
                               .check();



               } else {
                   TedPermission.create()
                           .setPermissionListener(permissionlistener)
                           .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                           .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                           .check();
               }


           }else{
               Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
               intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
               intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                       getString(R.string.device_admin_explanation));
               startActivity(intent);
           }

            }
        });


    }


    public void startBackgroundService() {
        if (!Utility.isMyServiceRunning(this, CameraService.class)) {
            ContextCompat.startForegroundService(this, new Intent(this, CameraService.class));
        }
    }

    public void stopBackgroundService() {
        if (Utility.isMyServiceRunning(this, CameraService.class)) {
            stopService(new Intent(SelfieModeActivate.this, CameraService.class));
        }
    }

    @Override
    protected void onResume() {

        if (Utility.isMyServiceRunning(this, CameraService.class)) {
            prefs.addPrefValues("status", true);
            activateText.setText("Activated");
            activate_service_ui.setImageResource(R.drawable.activate_service_on_ui);
            activateText.setTextColor(getColor(R.color.green));
            selfie_off_ui.setImageResource(R.drawable.selfie_on_ui);
        }


        super.onResume();

    }

    String[] permissions13 = {
            Manifest.permission.CAMERA,
            Manifest.permission.POST_NOTIFICATIONS
    };
    String[] permissionsSimple = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    void android13permissions(){
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.POST_NOTIFICATIONS
        };

        if (!checkPermissions(permissions)) {
            requestPermissions(permissions, REQUEST_CODE_PERMISSIONS);
        }
    }

    void simplePermissions(){
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        if (!checkPermissions(permissions)) {
            requestPermissions(permissions, REQUEST_CODE_PERMISSIONS);
        }
    }







    private boolean checkPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (checkPermissions(permissions)) {
                // Permissions granted, proceed with your logic

                if (prefs.getPrefValue("status")) {
                    prefs.addPrefValues("status", false);
                    selfie_off_ui.setImageResource(R.drawable.selfie_off_ui);
                    activateText.setText("Deactivated");
                    activate_service_ui.setImageResource(R.drawable.activate_service_ui);
                    activateText.setTextColor(getColor(R.color.red));
                    stopBackgroundService();
                } else {
                    prefs.addPrefValues("status", true);
                    activateText.setText("Activated");
                    activate_service_ui.setImageResource(R.drawable.activate_service_on_ui);
                    activateText.setTextColor(getColor(R.color.green));
                    selfie_off_ui.setImageResource(R.drawable.selfie_on_ui);

                    startBackgroundService();
                }

            } else {
                Toast.makeText(this, "Please allow the permissions from the settings.. the app won't work without these permissions.", Toast.LENGTH_LONG).show();
            }
        }
    }


}