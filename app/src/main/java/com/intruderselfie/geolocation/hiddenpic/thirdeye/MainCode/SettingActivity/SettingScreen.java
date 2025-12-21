package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.SettingActivity;

import android.Manifest;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.ads.NativeAdLayout;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.FbUtil;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.NativeAdView;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC.PasswordReceiver;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

public class SettingScreen extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private static final int BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE = 124;
    LabeledSwitch administratorSwitch, smsAlertSwitch, smsLocationAlertSwitch;
    private boolean isSpinnerInitialized = false;
    PrefManager prefManager;
    Spinner spinner;
    private FbUtil fbUtil;
    private NativeAdLayout nativeAdLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);

        prefManager = new PrefManager(this);
        administratorSwitch = findViewById(R.id.administratorSwitch);
        smsAlertSwitch = findViewById(R.id.smsAlertSwitch);
        smsLocationAlertSwitch = findViewById(R.id.smsLocationAlertSwitch);
        spinner = findViewById(R.id.spinner);
        String smsPermission1 = Manifest.permission.SEND_SMS;


        nativeAdLayout = findViewById(R.id.frameLayout);
        fbUtil = new FbUtil();
        fbUtil.loadNativeAd(
                this,
                getString(R.string.fbNative),
                nativeAdLayout,
                R.layout.fb_native_layout
        );


//        NativeAdView nativeAdView = new NativeAdView();
//        nativeAdView.loadNativeSplash(this);
        if (ContextCompat.checkSelfPermission(this, smsPermission1) != PackageManager.PERMISSION_GRANTED) {
            smsAlertSwitch.setEnabled(false);

        }


        administratorSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn){
                    if (!isAdminActive()){
                        administratorSwitch.setOn(true);
                        requestAdminPermission();
                    }else{
                        administratorSwitch.setOn(false);
                        disable();
                    }
                }else{
                    administratorSwitch.setOn(false);
                    disable();
                }
            }
        });

        smsAlertSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn) {
                    phoneNumberDialog();
                } else {
                    smsAlertSwitch.setOn(false);
                }
            }
        });

        findViewById(R.id.smsCardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndRequestSmsPermission();
            }
        });

        if (prefManager.getBooleanExtra("smsPurchase") || prefManager.getBooleanExtra("fullPurchase")){

            if (ContextCompat.checkSelfPermission(SettingScreen.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {


                    smsLocationAlertSwitch.setEnabled(true);

                    smsLocationAlertSwitch.setOnToggledListener(new OnToggledListener() {
                        @Override
                        public void onSwitched(ToggleableView toggleableView, boolean isOn) {



                            if (isOn){
                                // Premium Sub
                                prefManager.addToBoolean("smsON", true);
                                smsLocationAlertSwitch.setOn(true);
                            }else{
                                smsLocationAlertSwitch.setOn(false);
                            }

                        }
                    });


            } else {
                ActivityCompat.requestPermissions(SettingScreen.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
//            smsLocationAlertSwitch.setEnabled(false);

        }

        findViewById(R.id.smsLocationCardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!smsLocationAlertSwitch.isEnabled()){
                    Toast.makeText(SettingScreen.this, "Please Purchase First. Start Activity", Toast.LENGTH_SHORT).show();
                }
            }
        });




        String[] items = {"1 attempts", "2 attempts", "3 attempts", "4 attempts", "5 attempts"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);

                if (isSpinnerInitialized){
//                    Toast.makeText(SettingScreen.this, "Photo will be taken after: " + selectedItem, Toast.LENGTH_SHORT).show();

                    if (selectedItem.contains("1")){
                        prefManager.addToInteger("wrongAttempts", 1);

                    }else if (selectedItem.contains("2")){
                        prefManager.addToInteger("wrongAttempts", 2);
                    }else if (selectedItem.contains("3")){
                        prefManager.addToInteger("wrongAttempts", 3);
                    }else if (selectedItem.contains("4")){
                        prefManager.addToInteger("wrongAttempts", 4);
                    }else{
                        prefManager.addToInteger("wrongAttempts", 5);
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        isSpinnerInitialized = true;

    }


    public void phoneNumberDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingScreen.this);

        View customLayout = LayoutInflater.from(SettingScreen.this).inflate(R.layout.phone_no_dialog, null);
        builder.setView(customLayout);

        AlertDialog dialog = builder.create();

        dialog.setCancelable(false);

        EditText editText = customLayout.findViewById(R.id.phEditText);
        ImageView close = customLayout.findViewById(R.id.close);

        if (!prefManager.getStringExtra("ph").contains("a")){
            editText.setText(prefManager.getStringExtra("ph"));
        }
        ImageView save_ph = customLayout.findViewById(R.id.save_ph);

        save_ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ph = editText.getText().toString();

                if (ph.length() == 0){
                    editText.setError("Cannot be Empty");
                }else{
                    smsAlertSwitch.setOn(true);
                    prefManager.addToString("ph", ph);
                    dialog.cancel();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smsAlertSwitch.setOn(false);
                dialog.cancel();
            }
        });




        dialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (prefManager.getStringExtra("ph").contains("a")){
            smsAlertSwitch.setOn(false);
        }else{
            smsAlertSwitch.setOn(true);
        }

        if (isAdminActive()){
            administratorSwitch.setOn(true);
        }else{
            administratorSwitch.setOn(false);
        }


        if (prefManager.getBooleanExtra("smsON")){
            smsLocationAlertSwitch.setOn(true);
        }else{
            smsLocationAlertSwitch.setOn(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                            BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE);
                } else {
                }
            } else {
            }
        } else if (requestCode == BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                smsLocationAlertSwitch.setEnabled(true);

                smsLocationAlertSwitch.setOnToggledListener(new OnToggledListener() {
                    @Override
                    public void onSwitched(ToggleableView toggleableView, boolean isOn) {


                        //TODO Get Premium for sms location

                        if (isOn){
                            // Premium Sub
                            prefManager.addToBoolean("smsON", true);
                            smsLocationAlertSwitch.setOn(true);
                        }else{
                            smsLocationAlertSwitch.setOn(false);
                        }

                    }
                });

            } else {
                Toast.makeText(this, "This functionality won't work without background location permission", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_SMS_PERMISSION) {
            // Check if the permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, you can proceed with your SMS-related logic
                smsAlertSwitch.setEnabled(true);
                phoneNumberDialog();
            } else {
            }
        }
    }
    private static final int REQUEST_SMS_PERMISSION = 1;
    private void checkAndRequestSmsPermission() {
        String smsPermission = Manifest.permission.SEND_SMS;
        if (ContextCompat.checkSelfPermission(this, smsPermission) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{smsPermission}, REQUEST_SMS_PERMISSION);
        }
    }













    private boolean isAdminActive() {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(this, PasswordReceiver.class);
        return devicePolicyManager.isAdminActive(componentName);
    }

    private void requestAdminPermission() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        ComponentName componentName = new ComponentName(this, PasswordReceiver.class);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,  getString(R.string.device_admin_explanation));
        startActivityForResult(intent, REQUEST_ENABLE_ADMIN);
    }



    private static final int REQUEST_ENABLE_ADMIN = 789;



    void disable(){
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(this, PasswordReceiver.class);
        devicePolicyManager.removeActiveAdmin(componentName);
    }



}