package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.OnBoardingScreens;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.MainUi;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

public class PermissionActivity extends AppCompatActivity {

    private final int PERMISSION_REQUEST_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        findViewById(R.id.grantPermissions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

requestPermissions();

            }
        });
    }
    private void requestPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_EXTERNAL_STORAGE
                // Add other permissions as needed
        };

        // Check if permissions are granted
        if (checkPermissions(permissions)) {
            // All permissions are already granted, start your activity
            startYourActivity();
        } else {
            // Request permissions
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);




        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                // Check if all requested permissions are granted
                if (checkPermissions(permissions)) {
                    // All permissions are granted, start your activity
                    startYourActivity();
                } else {

                }
                break;
            }
            // Handle other permission requests if needed
        }
    }

    // Start your activity
    private void startYourActivity() {
        startActivity(new Intent(PermissionActivity.this, MainUi.class));
    }



}