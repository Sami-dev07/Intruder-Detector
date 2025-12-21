package com.intruderselfie.geolocation.hiddenpic.thirdeye.newBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.NativeAdLayout;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.AdsUtils;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.FbUtil;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.PermissionActivity1;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;


public class LanguageActivity extends AppCompatActivity {

    CheckBox check1, check2, check3, check4, check5;
    private FbUtil fbUtil;
    private NativeAdLayout nativeAdLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        nativeAdLayout = findViewById(R.id.frameLayout);
        fbUtil = new FbUtil();
        fbUtil.loadNativeAd(
                this,
                getString(R.string.fbNative),
                nativeAdLayout,
                R.layout.fb_native_layout
        );


        findViewById(R.id.checkSign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AdsUtils.showInterstitialAd(LanguageActivity.this, new Intent(LanguageActivity.this, PermissionManagerActivity.class));

                if (AdsUtils.mInterstitialAd != null) {
                    AdsUtils.mInterstitialAd.show(LanguageActivity.this);
                    AdsUtils.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            AdsUtils.mInterstitialAd = null;
                            AdsUtils.loadInterstitialAd(LanguageActivity.this);
                            startActivity(new Intent(LanguageActivity.this, PermissionActivity1.class));

                        }

                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            AdsUtils.mInterstitialAd = null;
                            startActivity(new Intent(LanguageActivity.this, PermissionActivity1.class));
                        }
                    });
                } else {
                    AdsUtils.loadInterstitialAd(LanguageActivity.this);
                    startActivity(new Intent(LanguageActivity.this, PermissionActivity1.class));
                }

            }
        });

        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        check3 = findViewById(R.id.check3);
        check4 = findViewById(R.id.check4);
        check5 = findViewById(R.id.check5);

        CompoundButton[] checkboxes = {check1, check2, check3, check4, check5};

        for (int i = 0; i < checkboxes.length; i++) {
            final int index = i;
            checkboxes[i].setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    for (int j = 0; j < checkboxes.length; j++) {
                        if (j != index) {
                            checkboxes[j].setChecked(false);
                        }
                    }
                }
            });

        }
    }
}