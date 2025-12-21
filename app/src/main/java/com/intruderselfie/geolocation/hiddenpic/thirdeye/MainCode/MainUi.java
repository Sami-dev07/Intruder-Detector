package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode;

import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAdLayout;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.AdsUtils;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.FbUtil;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.NativeAdView;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.InAppPurchase;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC.PasswordReceiver;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC.Utility;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.DriveActivities.DriveMainActivity;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.IntruderLocation.LocationMainActivity;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.IntruderSelfieActivities.ActivateServiceActivity;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.OnBoardingScreens.PrivacyPolicyActivity;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.SettingActivity.SettingScreen;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class MainUi extends AppCompatActivity {


    private InterstitialAd fbInterAd;
    private Animation animation;

    private InterstitialAdListener mInterstitialAdListener;

    ImageView adFreeIcon;
    PrefManager prefManager;
    private FbUtil fbUtil;
    private NativeAdLayout nativeAdLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);

        prefManager = new PrefManager(this);

        NativeAdView nativeAdView = new NativeAdView();
        nativeAdView.loadNativeGreen(this);


        adFreeIcon = findViewById(R.id.adFreeIcon);
        if (prefManager.getBooleanExtra("fullPurchase")){
            adFreeIcon.setVisibility(View.GONE);
        }else{
            adFreeIcon.setVisibility(View.VISIBLE);
        }
        Animation animation1 = new AlphaAnimation(1f, 0f);
        animation1.setDuration(1000);
        animation1.setRepeatMode(Animation.REVERSE);
        animation1.setRepeatCount(Animation.INFINITE);
        adFreeIcon.startAnimation(animation1);

        adFreeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainUi.this, InAppPurchase.class));
            }
        });



      if (!prefManager.getBooleanExtra("adsPurchase") || !prefManager.getBooleanExtra("fullPurchase")){
//          initFB();
      }


        animation = AnimationUtils.loadAnimation(this, R.anim.tap_animation);

        findViewById(R.id.selfie_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(animation);
                PrefManager.whichActivity = 0;


                Intent intent;
                if (prefManager.getBooleanExtra("privacy")){
                    intent = new Intent(MainUi.this, ActivateServiceActivity.class);
                }else{
                    intent = new Intent(MainUi.this, PrivacyPolicyActivity.class);
                }

                if (AdsUtils.adCounter % 3 == 0){
                    showInterstitialAd(intent);
                }else{
                    startActivity(intent);
                }
                AdsUtils.adCounter++;

                Log.e("Counter", "Counter: " + AdsUtils.adCounter);

            }
        });

        findViewById(R.id.location_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefManager.whichActivity = 1;
                view.startAnimation(animation);
//                startActivity(new Intent(MainUi.this, LocationMainActivity.class));


                location_ui();

            }
        });

        findViewById(R.id.drive_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefManager.whichActivity = 2;
                view.startAnimation(animation);

                drive_ui();
     }
        });

        findViewById(R.id.setting_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefManager.whichActivity = 3;
                view.startAnimation(animation);
                setting_ui();
            }
        });

    }

    void showInterstitialAd(Intent intent){
        if (AdsUtils.mInterstitialAd != null) {
            AdsUtils.mInterstitialAd.show(MainUi.this);
            AdsUtils.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    AdsUtils.mInterstitialAd = null;
                    AdsUtils.loadInterstitialAd(MainUi.this);
                    startActivity(intent);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    AdsUtils.mInterstitialAd = null;
                    startActivity(intent);

                }
            });
        }
        else {
            AdsUtils.loadInterstitialAd(MainUi.this);
            startActivity(intent);
        }
    }

    void location_ui(){
        Intent intent;

            if (prefManager.getBooleanExtra("privacy")){
                intent = new Intent(MainUi.this, LocationMainActivity.class);

            }else{
                intent = new Intent(MainUi.this, PrivacyPolicyActivity.class);
            }

            if (AdsUtils.adCounter % 3 == 0){
                showInterstitialAd(intent);
                AdsUtils.adCounter++;
            }else{
                startActivity(intent);
            }
        AdsUtils.adCounter++;


    }

    void drive_ui(){
        Intent intent;
        if (prefManager.getBooleanExtra("privacy")){
            intent = new Intent(MainUi.this, DriveMainActivity.class);

        }else{
            intent = new Intent(MainUi.this, PrivacyPolicyActivity.class);
        }

        if (AdsUtils.adCounter % 3 == 0){
            showInterstitialAd(intent);
        }else{
            startActivity(intent);
        }
        AdsUtils.adCounter++;

    }
    void setting_ui(){
        Intent intent;
        if (prefManager.getBooleanExtra("privacy")){
            intent = new Intent(MainUi.this, SettingScreen.class);
        }else{
            intent = new Intent(MainUi.this, PrivacyPolicyActivity.class);
        }


        if (AdsUtils.adCounter % 3 == 0){
            showInterstitialAd(intent);
        }else{
            startActivity(intent);
        }
        Log.e("Counter", "Counter: " + AdsUtils.adCounter);
        AdsUtils.adCounter++;

    }


//    private void initFB() {
//
//
//
//        mInterstitialAdListener = new InterstitialAdListener() {
//            @Override
//            public void onInterstitialDisplayed(Ad ad) {
//
//            }
//
//            @Override
//            public void onInterstitialDismissed(Ad ad) {
//                startActivity(new Intent(MainUi.this, LocationMainActivity.class));
//            }
//
//            @Override
//            public void onError(Ad ad, com.facebook.ads.AdError adError) {
//
//            }
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//
//            }
//
//            @Override
//            public void onLoggingImpression(Ad ad) {
//
//            }
//        };
//        fbInterAd.loadAd(
//                fbInterAd.buildLoadAdConfig().withAdListener(mInterstitialAdListener).build()
//        );
//
//
//    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainUi.this, ExitActivity.class));
    }


    @Override
    protected void onResume() {
        super.onResume();

        SmartLocation.with(this).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {

                        Utility.latitude = location.getLatitude();
                        Utility.longitude = location.getLongitude();

                    }
                });

        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, PasswordReceiver.class);
    }

    private DevicePolicyManager devicePolicyManager;
    private ComponentName componentName;


    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit App");
        builder.setMessage("Are you sure you want to exit?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainUi.this.finishAffinity();
            }
        });

        builder.setNeutralButton("Rate Us", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                rateUs();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    private void rateUs() {
        try {

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }


}