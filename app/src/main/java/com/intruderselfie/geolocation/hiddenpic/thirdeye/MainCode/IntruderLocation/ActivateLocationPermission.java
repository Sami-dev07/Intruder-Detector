package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.IntruderLocation;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.ads.NativeAdLayout;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.FbUtil;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.NativeAdView;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC.Utility;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class ActivateLocationPermission extends AppCompatActivity {

    ImageView activate_Location_ui, selfie_off_ui;
    TextView activateText;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private static final int BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE = 124;
    Animation animation;
    private FbUtil fbUtil;
    private NativeAdLayout nativeAdLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_location_permission);

        activate_Location_ui = findViewById(R.id.activate_Location_ui);
        activateText = findViewById(R.id.activateText);
        selfie_off_ui = findViewById(R.id.selfie_off_ui);
//        NativeAdView nativeAdView = new NativeAdView();
//        nativeAdView.loadNativeGreen(this);


        nativeAdLayout = findViewById(R.id.frameLayout);
        fbUtil = new FbUtil();
        fbUtil.loadNativeAd(
                this,
                getString(R.string.fbNative),
                nativeAdLayout,
                R.layout.fb_native_layout
        );


        animation = AnimationUtils.loadAnimation(this, R.anim.tap_animation);

        activate_Location_ui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(ActivateLocationPermission.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(ActivateLocationPermission.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED) {
                       showDialog();
//                    }
                } else {
                    ActivityCompat.requestPermissions(ActivateLocationPermission.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            LOCATION_PERMISSION_REQUEST_CODE);
                }


            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                activate_Location_ui.setImageResource(R.drawable.activate_service_on_ui);

                activateText.setText("Activated");
                activateText.setTextColor(getColor(R.color.green));
                selfie_off_ui.setImageResource(R.drawable.selfie_on_ui);



                SmartLocation.with(this).location()
                        .oneFix()
                        .start(new OnLocationUpdatedListener() {
                            @Override
                            public void onLocationUpdated(Location location) {

                                Utility.latitude = location.getLatitude();
                                Utility.longitude = location.getLongitude();

                            }
                        });


            } else {
            }
        }else{
            Toast.makeText(ActivateLocationPermission.this, "Location Permission is Required", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(ActivateLocationPermission.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {



            SmartLocation.with(this).location()
                    .oneFix()
                    .start(new OnLocationUpdatedListener() {
                        @Override
                        public void onLocationUpdated(Location location) {

                            Utility.latitude = location.getLatitude();
                            Utility.longitude = location.getLongitude();

                        }
                    });


            activate_Location_ui.setImageResource(R.drawable.activate_service_on_ui);

                activateText.setText("Activated");
                activateText.setTextColor(getColor(R.color.green));
                selfie_off_ui.setImageResource(R.drawable.selfie_on_ui);



        }
    }

    AlertDialog dialog;

    public void backPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivateLocationPermission.this);

        View customLayout = LayoutInflater.from(ActivateLocationPermission.this).inflate(R.layout.background_permissions, null);
        builder.setView(customLayout);

        dialog = builder.create();

        customLayout.findViewById(R.id.deny).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);

                dialog.cancel();
            }
        });

        customLayout.findViewById(R.id.allow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);

                if (ContextCompat.checkSelfPermission(ActivateLocationPermission.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(ActivateLocationPermission.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        activate_Location_ui.setImageResource(R.drawable.activate_service_on_ui);

                        activateText.setText("Activated");
                        activateText.setTextColor(getColor(R.color.green));
                        selfie_off_ui.setImageResource(R.drawable.selfie_on_ui);

                    } else {

                        ActivityCompat.requestPermissions(ActivateLocationPermission.this,
                                new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                                BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE);

                    }
                } else {
                    ActivityCompat.requestPermissions(ActivateLocationPermission.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            LOCATION_PERMISSION_REQUEST_CODE);
                }

            }
        });


        dialog.show();
    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Deactivate Location")
                .setMessage("Open app permissions in app info (Settings) to deactivate location permission for this app");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}