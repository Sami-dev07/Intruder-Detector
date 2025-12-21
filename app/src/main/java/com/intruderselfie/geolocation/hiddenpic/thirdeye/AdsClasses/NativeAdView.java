package com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

public class NativeAdView {


    public void loadNative(Activity activity) {
        AdLoader.Builder builder = new AdLoader.Builder(activity, activity.getResources().getString(R.string.nativeAdID));
        builder.forNativeAd(
                nativeAd -> {
                    FrameLayout frameLayout = activity.findViewById(R.id.frameLayout);
                    com.google.android.gms.ads.nativead.NativeAdView adView =
                            (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.adview_native, null);
                    populateNativeAdView(nativeAd, adView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(adView);
                });
        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);
        PrefManager prefInUtils = new PrefManager(activity);
        if (!prefInUtils.getBooleanExtra("adsPurchase") || !prefInUtils.getBooleanExtra("fullPurchase")){
            AdLoader adLoader = builder.withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                            Log.e("Get Native Error", "error: " + loadAdError);

                        }
                    })
                    .build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }

    }

    public void loadNativeSplash(Activity activity) {
        AdLoader.Builder builder = new AdLoader.Builder(activity, activity.getResources().getString(R.string.nativeAdID));
        builder.forNativeAd(
                nativeAd -> {
                    FrameLayout frameLayout = activity.findViewById(R.id.frameLayout);
                    com.google.android.gms.ads.nativead.NativeAdView adView =
                            (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.splash_native, null);
                    populateNativeAdView(nativeAd, adView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(adView);
                });
        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);


        PrefManager prefInUtils = new PrefManager(activity);
        if (!prefInUtils.getBooleanExtra("adsPurchase") || !prefInUtils.getBooleanExtra("fullPurchase")){
            AdLoader adLoader = builder.withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                            Log.e("Get Native Error", "error: " + loadAdError);

                        }
                    })
                    .build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }

    }
    public void loadNativeSplash1(Activity activity) {
        AdLoader.Builder builder = new AdLoader.Builder(activity, activity.getResources().getString(R.string.nativeAdID));
        builder.forNativeAd(
                nativeAd -> {
                    FrameLayout frameLayout = activity.findViewById(R.id.frameLayout);
                    com.google.android.gms.ads.nativead.NativeAdView adView =
                            (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.main_splash_ad, null);
                    populateNativeAdView(nativeAd, adView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(adView);
                });
        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);


        PrefManager prefInUtils = new PrefManager(activity);
        if (!prefInUtils.getBooleanExtra("adsPurchase") || !prefInUtils.getBooleanExtra("fullPurchase")){
            AdLoader adLoader = builder.withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                            Log.e("Get Native Error", "error: " + loadAdError);

                        }
                    })
                    .build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }

    }

    public void loadNativeGreen(Activity activity) {
        AdLoader.Builder builder = new AdLoader.Builder(activity, activity.getResources().getString(R.string.nativeAdID));
        builder.forNativeAd(
                nativeAd -> {
                    FrameLayout frameLayout = activity.findViewById(R.id.frameLayout);
                    com.google.android.gms.ads.nativead.NativeAdView adView =
                            (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.native_green, null);
                    populateNativeAdView(nativeAd, adView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(adView);
                });
        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);


        PrefManager prefInUtils = new PrefManager(activity);
        if (!prefInUtils.getBooleanExtra("adsPurchase") || !prefInUtils.getBooleanExtra("fullPurchase")){
            AdLoader adLoader = builder.withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                            Log.e("Get Native Error", "error: " + loadAdError);

                        }
                    })
                    .build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }

    }

    private void populateNativeAdView(NativeAd nativeAd, com.google.android.gms.ads.nativead.NativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }
        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }
        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
        VideoController vc = nativeAd.getMediaContent().getVideoController();
        if (vc.hasVideoContent()) {
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        } else {
        }
    }

}
