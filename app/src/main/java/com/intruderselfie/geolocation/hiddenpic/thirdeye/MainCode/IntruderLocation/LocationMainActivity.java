package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.IntruderLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.AdsUtils;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.NativeAdView;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

public class LocationMainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_main);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.tap_animation);


        NativeAdView nativeAdView = new NativeAdView();
        nativeAdView.loadNativeSplash(this);

        findViewById(R.id.activate_location_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);


                if (AdsUtils.adCounter % 3 == 0){
                    if (AdsUtils.mInterstitialAd != null){
                        AdsUtils.mInterstitialAd.show(LocationMainActivity.this);
                        AdsUtils.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                AdsUtils.mInterstitialAd = null;
                                AdsUtils.loadInterstitialAd(LocationMainActivity.this);

                                startActivity(new Intent(LocationMainActivity.this, ActivateLocationPermission.class));

                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                AdsUtils.mInterstitialAd = null;
                                startActivity(new Intent(LocationMainActivity.this, ActivateLocationPermission.class));

                            }
                        });
                    }else{
                        AdsUtils.loadInterstitialAd(LocationMainActivity.this);
                        startActivity(new Intent(LocationMainActivity.this, ActivateLocationPermission.class));

                    }
                }else{
                    startActivity(new Intent(LocationMainActivity.this, ActivateLocationPermission.class));
                }

                AdsUtils.adCounter++;



            }
        });

        findViewById(R.id.saved_location_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
//                startActivity(new Intent(LocationMainActivity.this, SavedIntruderLocations.class));
              if (AdsUtils.adCounter % 3 == 0){
                  if (AdsUtils.mInterstitialAd != null){
                      AdsUtils.mInterstitialAd.show(LocationMainActivity.this);
                      AdsUtils.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                          @Override
                          public void onAdDismissedFullScreenContent() {
                              super.onAdDismissedFullScreenContent();
                              AdsUtils.mInterstitialAd = null;
                              AdsUtils.loadInterstitialAd(LocationMainActivity.this);

                              startActivity(new Intent(LocationMainActivity.this, SavedIntruderLocations.class));
                          }

                          @Override
                          public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                              super.onAdFailedToShowFullScreenContent(adError);
                              AdsUtils.mInterstitialAd = null;
                              startActivity(new Intent(LocationMainActivity.this, SavedIntruderLocations.class));
                          }
                      });
                  }else{
                      AdsUtils.loadInterstitialAd(LocationMainActivity.this);
                      startActivity(new Intent(LocationMainActivity.this, SavedIntruderLocations.class));
                  }
              }else{
                  startActivity(new Intent(LocationMainActivity.this, SavedIntruderLocations.class));
              }

              AdsUtils.adCounter++;
            }
        });


    }


}