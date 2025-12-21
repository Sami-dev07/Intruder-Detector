package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.OnBoardingScreens;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.facebook.ads.NativeAdLayout;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.FbUtil;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.DriveActivities.DriveMainActivity;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.IntruderLocation.LocationMainActivity;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.IntruderSelfieActivities.ActivateServiceActivity;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.SettingActivity.SettingScreen;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    AppCompatButton agreeBtn;
    CheckBox agreedCheckbox;
    TextView tv_link;
    PrefManager prefManager;
    private FbUtil fbUtil;
    private NativeAdLayout nativeAdLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

//        NativeAdView nativeAdView = new NativeAdView();
//        nativeAdView.loadNative(this);
        nativeAdLayout = findViewById(R.id.frameLayout);
        fbUtil = new FbUtil();
        fbUtil.loadNativeAd(
                this,
                getString(R.string.fbNative),
                nativeAdLayout,
                R.layout.fb_native_layout
        );


        prefManager = new PrefManager(this);
        agreeBtn = findViewById(R.id.agreeBtn);
        agreedCheckbox = findViewById(R.id.agreedCheckbox);
        tv_link = findViewById(R.id.tv_link);
        agreedCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (agreedCheckbox.isChecked()) {
                agreeBtn.setVisibility(View.VISIBLE);
            } else {
                agreeBtn.setVisibility(View.GONE);
            }
        });

        agreeBtn.setOnClickListener(v -> {

//
//            if (AdsUtils.mInterstitialAd != null) {
//                AdsUtils.mInterstitialAd.show(PrivacyPolicyActivity.this);
//                AdsUtils.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                    @Override
//                    public void onAdDismissedFullScreenContent() {
//                        super.onAdDismissedFullScreenContent();
//                        AdsUtils.mInterstitialAd = null;
//                        AdsUtils.loadInterstitialAd(PrivacyPolicyActivity.this);
//                        privacyStart();
//
//                    }
//
//                    //
//                    @Override
//                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
//                        super.onAdFailedToShowFullScreenContent(adError);
//                        AdsUtils.mInterstitialAd = null;
//                        privacyStart();
//                    }
//                });
//            } else {
//                AdsUtils.loadInterstitialAd(PrivacyPolicyActivity.this);
            privacyStart();
//            }


        });
        tv_link.setClickable(true);
        tv_link.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='https://gossipeasy.com/privacy-policy/'>Privacy Policy</a>";
        tv_link.setText(Html.fromHtml(text));
    }

    void privacyStart() {
        prefManager.addToBoolean("privacy", true);

        if (PrefManager.whichActivity == 0) {
            startActivity(new Intent(PrivacyPolicyActivity.this, ActivateServiceActivity.class));

        } else if (PrefManager.whichActivity == 1) {
            startActivity(new Intent(PrivacyPolicyActivity.this, LocationMainActivity.class));

        } else if (PrefManager.whichActivity == 2) {
            startActivity(new Intent(PrivacyPolicyActivity.this, DriveMainActivity.class));

        } else if (PrefManager.whichActivity == 3) {
            startActivity(new Intent(PrivacyPolicyActivity.this, SettingScreen.class));

        }

        finish();
    }

}