package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.OnBoardingScreens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.PurchaseInfo
import com.facebook.ads.NativeAdLayout
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.AdsUtils
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.FbUtil
import com.intruderselfie.geolocation.hiddenpic.thirdeye.BaseActivity
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R
import com.intruderselfie.geolocation.hiddenpic.thirdeye.newBoarding.OnboardingActivity

class SplashScreenActivity : BaseActivity() {

    var progressBar: ProgressBar? = null
    var getStartedCardView: CardView? = null
    var animation: Animation? = null
    var prefManager: PrefManager? = null

    private var fbUtil: FbUtil? = null
    private var nativeAdLayout: NativeAdLayout? = null

    //inApp Purchase
    private val MERCHANT_ID: String? = null
    private val LICENSE_KEY =
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhC3fkqYqE4ZP1IKuLcXPrWchO854MYmTH+UpGXNqz442qQRhiLHqApqJcUxeqkotKrgZuiChUVS9clUc5JnIfqfW6LlCpDnhhr7gZEdmmKrdSqPCo9iAfixKzA2PHQ+SUVdUPC+AId+iQzqfvTBERzmU2XkvITxrLQ5wFWDqbe4CrevxesJCzV3Ri0Ptbh9Qio1P5CNGEu5+dW1vi04Isuw/d3EQkHKQX3+pyn13PIZFuxJ1RxDUSoH6ltxsZr3vpKBPOhvPfHAHlqIPAh4mIbAXVE0v+TOpAyoGRRgGdOJcrwl0jIW09PxKO48fSk5S7lbM4oifwjXX09x9hBwWpQIDAQAB"

    val driveId: String = "drivepurchase_productid"
    val adsID: String = "adspurchase_productid"
    val fullID: String = "allproducts_productid"
    private var bp: BillingProcessor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        progressBar = findViewById(R.id.progressBar)
        prefManager = PrefManager(this)

        nativeAdLayout = findViewById(R.id.frameLayout)
        fbUtil = FbUtil()



        AdsUtils.loadInterstitialAd(this)
        animation = AnimationUtils.loadAnimation(this, R.anim.tap_animation)
        getStartedCardView = findViewById(R.id.getStartedCardView)



        Handler().postDelayed({

            /*progressBar?.visibility = View.GONE
            getStartedCardView?.visibility = View.VISIBLE*/


            if (AdsUtils.mInterstitialAd != null) {
                AdsUtils.mInterstitialAd.show(this@SplashScreenActivity)
                AdsUtils.mInterstitialAd.fullScreenContentCallback =
                    object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            AdsUtils.mInterstitialAd = null
                            AdsUtils.loadInterstitialAd(this@SplashScreenActivity)
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity, SelfieOnBoardScreen::class.java
                                )
                            )
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            super.onAdFailedToShowFullScreenContent(adError)
                            AdsUtils.mInterstitialAd = null
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity, SelfieOnBoardScreen::class.java
                                )
                            )
                        }
                    }
            } else {
                AdsUtils.loadInterstitialAd(this@SplashScreenActivity)
                startActivity(Intent(this@SplashScreenActivity, SelfieOnBoardScreen::class.java))
            }

        }, 6000)


        getStartedCardView?.setOnClickListener(View.OnClickListener {
            getStartedCardView?.startAnimation(animation)

            if (AdsUtils.mInterstitialAd != null) {
                AdsUtils.mInterstitialAd.show(this@SplashScreenActivity)
                AdsUtils.mInterstitialAd.fullScreenContentCallback =
                    object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            AdsUtils.mInterstitialAd = null
                            AdsUtils.loadInterstitialAd(this@SplashScreenActivity)
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity, SelfieOnBoardScreen::class.java
                                )
                            )
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            super.onAdFailedToShowFullScreenContent(adError)
                            AdsUtils.mInterstitialAd = null
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity, SelfieOnBoardScreen::class.java
                                )
                            )
                        }
                    }
            } else {
                AdsUtils.loadInterstitialAd(this@SplashScreenActivity)
                startActivity(Intent(this@SplashScreenActivity, SelfieOnBoardScreen::class.java))
            }

//


        })



        bp = BillingProcessor(this,
            LICENSE_KEY,
            MERCHANT_ID,
            object : BillingProcessor.IBillingHandler {
                override fun onProductPurchased(productId: String, purchaseInfo: PurchaseInfo?) {

                    if (adsID == productId) {
                        prefManager?.addToBoolean("adsPurchase", true)
                    }
                    if (driveId == productId) {
                        prefManager?.addToBoolean("drivePurchase", true)
                    }

                    if (fullID == productId) {
                        prefManager?.addToBoolean("fullPurchase", true)
                    }


                }

                override fun onBillingError(errorCode: Int, error: Throwable?) {
                }

                override fun onBillingInitialized() {

                }

                override fun onPurchaseHistoryRestored() {

                }
            })


        if (bp!!.isPurchased(adsID)) {
            prefManager?.addToBoolean("adsPurchase", true)
        }
        if (bp!!.isPurchased(driveId)) {
            prefManager?.addToBoolean("drivePurchase", true)
        }
        if (bp!!.isPurchased(fullID)) {
            prefManager?.addToBoolean("fullPurchase", true)
        }

    }


    fun go() {

        startActivity(
            Intent(
                this@SplashScreenActivity, OnboardingActivity::class.java
            )
        )

        finish()
    }


}