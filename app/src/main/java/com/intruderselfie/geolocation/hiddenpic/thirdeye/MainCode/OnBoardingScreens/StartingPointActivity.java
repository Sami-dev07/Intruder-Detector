package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.OnBoardingScreens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.facebook.ads.NativeAdLayout;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.AdsUtils;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.FbUtil;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.NativeAdView;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.MainUi;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

public class StartingPointActivity extends AppCompatActivity {


    ProgressBar progressBar;
    CardView getStartedCardView;

    Animation animation;
    PrefManager prefManager;

    private FbUtil fbUtil;
    private NativeAdLayout nativeAdLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_point);


        progressBar = findViewById(R.id.progressBar);

        prefManager = new PrefManager(this);

        NativeAdView nativeAdView = new NativeAdView();
        nativeAdView.loadNativeSplash(this);

        nativeAdLayout = findViewById(R.id.frameLayout);
        fbUtil = new FbUtil();
        fbUtil.loadNativeAd(
                this,
                getResources().getString(R.string.fbNative),
                nativeAdLayout,
                R.layout.fb_native_layout
        );


        AdsUtils.loadInterstitialAd(this);

        animation = AnimationUtils.loadAnimation(this, R.anim.tap_animation);
        getStartedCardView = findViewById(R.id.getStartedCardView);
        getStartedCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getStartedCardView.startAnimation(animation);


                if (AdsUtils.mInterstitialAd != null) {
                    AdsUtils.mInterstitialAd.show(StartingPointActivity.this);
                    AdsUtils.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            AdsUtils.mInterstitialAd = null;
                            AdsUtils.loadInterstitialAd(StartingPointActivity.this);
                            if (prefManager.getBooleanExtra("onBoard")) {
                                startActivity(new Intent(StartingPointActivity.this, MainUi.class));
                            } else {
                                startActivity(new Intent(StartingPointActivity.this, SelfieOnBoardScreen.class));

                            }
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            AdsUtils.mInterstitialAd = null;
                            if (prefManager.getBooleanExtra("onBoard")) {
                                startActivity(new Intent(StartingPointActivity.this, MainUi.class));
                            } else {
                                startActivity(new Intent(StartingPointActivity.this, SelfieOnBoardScreen.class));

                            }
                        }
                    });
                } else {
                    AdsUtils.loadInterstitialAd(StartingPointActivity.this);
                    if (prefManager.getBooleanExtra("onBoard")) {
                        startActivity(new Intent(StartingPointActivity.this, MainUi.class));
                    } else {
                        startActivity(new Intent(StartingPointActivity.this, SelfieOnBoardScreen.class));

                    }
                }

//


            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                getStartedCardView.setVisibility(View.VISIBLE);
            }
        }, 6000);


    }


}