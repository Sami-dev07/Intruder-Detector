package com.intruderselfie.geolocation.hiddenpic.thirdeye

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Toast
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.PurchaseInfo
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.MainUi
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager

class InAppPurchase : AppCompatActivity() {

    val driveId: String = "drivepurchase_productid"
    val adsID: String = "adspurchase_productid"
    val fullID: String = "allproducts_productid"

    var adsFreeCheckBox: CheckBox? = null
    var autoGoogleDriveSyncCheckBox: CheckBox? = null
    var unlockAllFeaturesCheckBox: CheckBox? = null
    var prefManager: PrefManager? = null
    //inApp Purchase
    private val MERCHANT_ID: String? = null
    private val LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhC3fkqYqE4ZP1IKuLcXPrWchO854MYmTH+UpGXNqz442qQRhiLHqApqJcUxeqkotKrgZuiChUVS9clUc5JnIfqfW6LlCpDnhhr7gZEdmmKrdSqPCo9iAfixKzA2PHQ+SUVdUPC+AId+iQzqfvTBERzmU2XkvITxrLQ5wFWDqbe4CrevxesJCzV3Ri0Ptbh9Qio1P5CNGEu5+dW1vi04Isuw/d3EQkHKQX3+pyn13PIZFuxJ1RxDUSoH6ltxsZr3vpKBPOhvPfHAHlqIPAh4mIbAXVE0v+TOpAyoGRRgGdOJcrwl0jIW09PxKO48fSk5S7lbM4oifwjXX09x9hBwWpQIDAQAB"


        private var bp: BillingProcessor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_app_purchase)



        prefManager = PrefManager(this)

        adsFreeCheckBox = findViewById(R.id.adsFreeCheckBox)
        autoGoogleDriveSyncCheckBox = findViewById(R.id.autoGoogleDriveSyncCheckBox)
        unlockAllFeaturesCheckBox = findViewById(R.id.unlockAllFeaturesCheckBox)
        adsFreeCheckBox?.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                adsFreeCheckBox?.isChecked = true
                autoGoogleDriveSyncCheckBox?.isChecked = false
                unlockAllFeaturesCheckBox?.isChecked = false
            }
        }
        autoGoogleDriveSyncCheckBox?.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                autoGoogleDriveSyncCheckBox?.isChecked = true
                adsFreeCheckBox?.isChecked = false
                unlockAllFeaturesCheckBox?.isChecked = false
            }
        }
        unlockAllFeaturesCheckBox?.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                unlockAllFeaturesCheckBox?.isChecked = true
                adsFreeCheckBox?.isChecked = false
                autoGoogleDriveSyncCheckBox?.isChecked = false
            }
        }


        bp = BillingProcessor(
            this,
            LICENSE_KEY,
            MERCHANT_ID,
            object : BillingProcessor.IBillingHandler {
                override fun onProductPurchased(productId: String, purchaseInfo: PurchaseInfo?) {

                    if (adsID == productId){
                        prefManager?.addToBoolean("adsPurchase", true)
                    }
                    if (driveId == productId){
                        prefManager?.addToBoolean("drivePurchase", true)
                    }

                    if (fullID == productId){
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


        findViewById<ImageView>(R.id.purchaseNow).setOnClickListener {

            if (adsFreeCheckBox?.isChecked == true) {


                try {
                    bp?.purchase(this@InAppPurchase, adsID)
                } catch (e: Exception){
                    Log.e("Get Purchasing Error", "error: $e")
                    Toast.makeText(this@InAppPurchase, "Some Error Occurred", Toast.LENGTH_SHORT).show()

                }

            } else if (autoGoogleDriveSyncCheckBox?.isChecked == true) {
                try {
                    bp?.purchase(this@InAppPurchase, driveId)
                } catch (e: Exception){
                    Log.e("Get Purchasing Error", "error: $e")
                    Toast.makeText(this@InAppPurchase, "Some Error Occurred", Toast.LENGTH_SHORT).show()

                }

            } else if (unlockAllFeaturesCheckBox?.isChecked == true) {
                try {
                    bp?.purchase(this@InAppPurchase, fullID)
                } catch (e: Exception){
                    Log.e("Get Purchasing Error", "error: $e")
                    Toast.makeText(this@InAppPurchase, "Some Error Occurred", Toast.LENGTH_SHORT).show()

                }

            } else {
                Toast.makeText(this@InAppPurchase, "Please Select an option first.", Toast.LENGTH_SHORT).show()
            }

        }



        findViewById<ImageView>(R.id.continueWithAds).setOnClickListener {
            startActivity(Intent(this@InAppPurchase, MainUi::class.java))

        }

    }
}