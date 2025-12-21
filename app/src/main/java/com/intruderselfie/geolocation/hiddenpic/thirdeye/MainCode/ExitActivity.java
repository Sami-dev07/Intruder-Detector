package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.FbUtil;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.NativeAdView;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

public class ExitActivity extends AppCompatActivity {

    FbUtil fbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);

//        fbUtil = new FbUtil();
//        fbUtil.loadFbNativeBanner(this, findViewById(R.id.native_banner_ad_container), getString(R.string.fbNativeBanner));
        /*NativeAdView nativeAdView = new NativeAdView();
        nativeAdView.loadNativeGreen(this);*/
        findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExitActivity.this.finishAffinity();
            }
        });

        findViewById(R.id.rateUS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateUs();
            }
        });

        findViewById(R.id.tv_privacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://gossipeasy.com/privacy-policy/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });


    }

    private void rateUs() {
        try {

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }


//    private NativeAdLayout nativeAdLayout;
//    private LinearLayout adView;
//    private NativeBannerAd nativeBannerAd;
//
//
//    @Override
//    protected void onResume() {
//        loadNativeBanner();
//        super.onResume();
//    }
//
//    void loadNativeBanner(){
//        nativeBannerAd = new NativeBannerAd(this, getString(R.string.fbNativeBanner));
//        NativeAdListener nativeAdListener = new NativeAdListener() {
//            @Override
//            public void onMediaDownloaded(Ad ad) {
//
//            }
//
//            @Override
//            public void onError(Ad ad, AdError adError) {
//
//            }
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//                // Race condition, load() called again before last ad was displayed
//                if (nativeBannerAd == null || nativeBannerAd != ad) {
//                    return;
//                }
//                // Inflate Native Banner Ad into Container
//                inflateAd(nativeBannerAd);
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//
//            }
//
//            @Override
//            public void onLoggingImpression(Ad ad) {
//
//            }
//        };
//        // load the ad
//        nativeBannerAd.loadAd(
//                nativeBannerAd.buildLoadAdConfig()
//                        .withAdListener(nativeAdListener)
//                        .build());
//    }
//    private void inflateAd(NativeBannerAd nativeBannerAd) {
//        // Unregister last ad
//        nativeBannerAd.unregisterView();
//
//        // Add the Ad view into the ad container.
//        nativeAdLayout = findViewById(R.id.native_banner_ad_container);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
//        adView = (LinearLayout) inflater.inflate(R.layout.fb_native_banner, nativeAdLayout, false);
//        nativeAdLayout.addView(adView);
//
//        // Add the AdChoices icon
//        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
//        AdOptionsView adOptionsView = new AdOptionsView(this, nativeBannerAd, nativeAdLayout);
//        adChoicesContainer.removeAllViews();
//        adChoicesContainer.addView(adOptionsView, 0);
//
//        // Create native UI using the ad metadata.
//        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
//        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
//        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
//        MediaView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
//        AppCompatButton nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);
//
//        // Set the Text.
//        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
//        nativeAdCallToAction.setVisibility(
//                nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
//        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
//        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
//        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());
//
//        // Register the Title and CTA button to listen for clicks.
//        List<View> clickableViews = new ArrayList<>();
//        clickableViews.add(nativeAdTitle);
//        clickableViews.add(nativeAdCallToAction);
//        nativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
//    }
}