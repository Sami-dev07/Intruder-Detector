package com.intruderselfie.geolocation.hiddenpic.thirdeye;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.MainUi;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.OnBoardingScreens.PermissionActivity;

import java.util.List;

public class PermissionActivity1 extends AppCompatActivity {


    boolean isAllowed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                isAllowed = true;
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
            }


        };

        findViewById(R.id.grantPermissions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isAllowed){
                    startActivity(new Intent(PermissionActivity1.this, MainUi.class));
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                        TedPermission.create()
                                .setPermissionListener(permissionlistener)
                                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.CAMERA)

                                .check();
                    }else{
                        TedPermission.create()
                                .setPermissionListener(permissionlistener)
                                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                                .check();
                    }
                }




            }
        });




    }
}