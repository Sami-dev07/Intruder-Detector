package com.intruderselfie.geolocation.hiddenpic.thirdeye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.OpenAppAdManager;

import java.util.Date;

public class LoadingAd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_ad);
        fetchAd();
    }


    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    void fetchAd(){
        loadCallback =
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                        super.onAdLoaded(appOpenAd);
                        appOpenAd.show(LoadingAd.this);
                        appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                onBackPressed();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                onBackPressed();
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Log.e("Get Open Ad Error", "Error" + loadAdError);
                        onBackPressed();
                    }

                };
        AdRequest request = getAdRequest();
        AppOpenAd.load(
                this,  getApplicationContext().getResources().getString(R.string.appOpenAdID), request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }
}