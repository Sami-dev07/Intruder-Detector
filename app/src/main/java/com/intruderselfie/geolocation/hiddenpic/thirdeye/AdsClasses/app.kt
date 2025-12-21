package com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.facebook.ads.AudienceNetworkAds
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.intruderselfie.geolocation.hiddenpic.thirdeye.BaseActivity
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager


class app : Application() {
    override fun onCreate() {
        super.onCreate()


        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (activity is BaseActivity) {
                    activity.requestConsentForm()
                    MobileAds.initialize(this@app)
                    initAd()
                }

            }

            override fun onActivityStarted(activity: Activity) {}

            override fun onActivityResumed(activity: Activity) {}

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {

            }
        })

AudienceNetworkAds.initialize(this@app)
        MobileAds.initialize(
            this
        ) { initializationStatus ->
            val statusMap = initializationStatus.adapterStatusMap
            for (adapterClass in statusMap.keys) {
                val status = statusMap[adapterClass]
                Log.e(
                    "MyApp Adapter", String.format(
                        "Adapter name: %s, Description: %s, Latency: %d",
                        adapterClass, status!!.description, status.latency
                    )
                )
            }



//            MobileAds.openAdInspector(this@app) {
//                // Error will be non-null if ad inspector closed due to an error.
//            }

            // Start loading ads here...
        }


        val prefInUtils = PrefManager(this)
        if (!prefInUtils.getBooleanExtra("adsPurchase") || !prefInUtils.getBooleanExtra("fullPurchase")) {
            OpenAppAdManager(this)
        }
        FirebaseApp.initializeApp(this)
        FirebaseAnalytics.getInstance(this)
        FirebaseCrashlytics.getInstance()
    }

    private fun initAd() {
        AudienceNetworkAds.initialize(this)
        //AdSettings.getTestAdType();
        ///AdSettings.setDebugBuild(true);
    }
}