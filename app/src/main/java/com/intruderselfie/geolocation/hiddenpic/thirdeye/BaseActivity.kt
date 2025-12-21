package com.intruderselfie.geolocation.hiddenpic.thirdeye

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform
import java.util.concurrent.atomic.AtomicBoolean

open class BaseActivity : AppCompatActivity() {
    private lateinit var consentInformation: ConsentInformation
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    fun requestConsentForm() {
        val debugSettings = ConsentDebugSettings.Builder(this)
            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
          //  .addTestDeviceHashedId("6C2EEB7E56760F3D1AE644B9F54E637B")
            .build()
        val params = ConsentRequestParameters.Builder()
            .setConsentDebugSettings(debugSettings)
            .setTagForUnderAgeOfConsent(false)
            .build()
        consentInformation = UserMessagingPlatform.getConsentInformation(this)
        //consentInformation.reset() // TODO comment while releasing
        consentInformation.requestConsentInfoUpdate(
            this,
            params,
            {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                    this
                ) { loadAndShowError: FormError? ->
                    if (loadAndShowError != null) {
                        Log.w(
                            ContentValues.TAG, String.format(
                                "%s: %s",
                                loadAndShowError.errorCode,
                                loadAndShowError.message
                            )
                        )
                    }
                    if (consentInformation.canRequestAds()) {
                        initializeMobileAdsSdk()
                    }
                }
            },
            { requestConsentError: FormError ->
                Log.w(
                    ContentValues.TAG, String.format(
                        "%s: %s",
                        requestConsentError.errorCode,
                        requestConsentError.message
                    )
                )
            })
        if (consentInformation.canRequestAds()) {
            initializeMobileAdsSdk()
        }
    }
    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return
        }
    }
}