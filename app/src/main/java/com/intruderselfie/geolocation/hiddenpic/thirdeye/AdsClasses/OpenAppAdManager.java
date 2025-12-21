package com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses;

import static androidx.lifecycle.Lifecycle.Event.ON_RESUME;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.LoadingAd;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.OnBoardingScreens.SplashScreenActivity;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.PermissionActivity1;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

import java.util.Date;

public class OpenAppAdManager implements LifecycleObserver, Application.ActivityLifecycleCallbacks {

    private static final String LOG_TAG = "OpenAppAdManager";
    private AppOpenAd appOpenAd = null;
    private long loadTime = 0;
    private static boolean isShowingAd = false;
    private Activity currentActivity;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    Application application;


    public OpenAppAdManager(Application myApplication) {
        this.application = myApplication;
        this.application.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    public boolean isAdAvailable() {
        return this.appOpenAd != null && wasLoadTimeLessThanNHoursAgo();
    }

    public void fetchAd() {
        if (isAdAvailable()) {
            return;
        }
        loadCallback =
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                        super.onAdLoaded(appOpenAd);
                        OpenAppAdManager.this.appOpenAd = appOpenAd;
                        OpenAppAdManager.this.loadTime = (new Date()).getTime();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Log.e("Get Open Ad Error", "Error" + loadAdError);
                    }

                };
        AdRequest request = getAdRequest();
        AppOpenAd.load(
                application, application.getApplicationContext().getResources().getString(R.string.appOpenAdID), request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }


    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        currentActivity = null;
    }


    public void showAdIfAvailable() {

        if (!currentActivity.getClass().getName().equals(SplashScreenActivity.class.getName()) && !currentActivity.getClass().getName().equals(PermissionActivity1.class.getName())){
            currentActivity.startActivity(new Intent(currentActivity, LoadingAd.class));
        }



    }
    @OnLifecycleEvent(ON_RESUME)
    protected void onResume() {
        showAdIfAvailable();
    }

    private boolean wasLoadTimeLessThanNHoursAgo() {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * (long) 4));
    }


}