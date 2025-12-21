package com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;


import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AdsUtils {

    public static int adCounter = 0;

    public static InterstitialAd mInterstitialAd;
    public static void loadInterstitialAd(Activity activity){
        Log.e("Counter", "Counter: " + AdsUtils.adCounter);

        PrefManager prefInUtils = new PrefManager(activity);
        if (!prefInUtils.getBooleanExtra("adsPurchase") || !prefInUtils.getBooleanExtra("fullPurchase")){
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(activity, activity.getString(R.string.interstitialAdID), adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            mInterstitialAd = interstitialAd;
                            Log.i("TAG", "onAdLoaded");
                        }
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.e("TAG Interstitial", loadAdError.toString());
                            mInterstitialAd = null;
                        }
                    });
        }

    }

}
