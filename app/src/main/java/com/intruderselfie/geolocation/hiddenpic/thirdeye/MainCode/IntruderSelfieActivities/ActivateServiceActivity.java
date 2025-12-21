package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.IntruderSelfieActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.NativeAdLayout;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.AdsUtils;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.FbUtil;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.IntruderSavedImages.IntruderImagesActivity;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

public class ActivateServiceActivity extends AppCompatActivity {


    private FbUtil fbUtil;
    private NativeAdLayout nativeAdLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_service);

//        AdsUtils.loadInterstitialAd(this);
        fbUtil = new FbUtil();


        nativeAdLayout = findViewById(R.id.frameLayout);
        fbUtil.loadNativeAd(
                this,
                getString(R.string.fbNative),
                nativeAdLayout,
                R.layout.fb_native_layout
        );


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.tap_animation);
        findViewById(R.id.selfie_mode_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (AdsUtils.adCounter % 3 == 0) {
                    if (AdsUtils.mInterstitialAd != null) {

                        AdsUtils.mInterstitialAd.show(ActivateServiceActivity.this);
                        AdsUtils.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                AdsUtils.mInterstitialAd = null;
                                startActivity(new Intent(ActivateServiceActivity.this, SelfieModeActivate.class));
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                AdsUtils.mInterstitialAd = null;
                                startActivity(new Intent(ActivateServiceActivity.this, SelfieModeActivate.class));
                            }
                        });
                    } else {

                        view.startAnimation(animation);
                        startActivity(new Intent(ActivateServiceActivity.this, SelfieModeActivate.class));
                    }
                } else {
                    view.startAnimation(animation);
                    startActivity(new Intent(ActivateServiceActivity.this, SelfieModeActivate.class));
                }

                AdsUtils.adCounter++;

            }
        });

        findViewById(R.id.saved_selfie_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);

                if (AdsUtils.adCounter % 3 == 0) {

                    if (AdsUtils.mInterstitialAd != null) {
                        AdsUtils.mInterstitialAd.show(ActivateServiceActivity.this);
                        AdsUtils.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                AdsUtils.mInterstitialAd = null;
                                AdsUtils.loadInterstitialAd(ActivateServiceActivity.this);
                                startActivity(new Intent(ActivateServiceActivity.this, IntruderImagesActivity.class));
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                AdsUtils.mInterstitialAd = null;
                                startActivity(new Intent(ActivateServiceActivity.this, IntruderImagesActivity.class));
                            }
                        });
                    } else {
                        AdsUtils.loadInterstitialAd(ActivateServiceActivity.this);
                        startActivity(new Intent(ActivateServiceActivity.this, IntruderImagesActivity.class));
                    }
                } else {

                    startActivity(new Intent(ActivateServiceActivity.this, IntruderImagesActivity.class));

                }

                AdsUtils.adCounter++;

            }
        });
    }
}