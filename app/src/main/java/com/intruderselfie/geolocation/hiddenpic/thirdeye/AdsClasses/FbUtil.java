package com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

import java.util.ArrayList;
import java.util.List;

public class FbUtil {

    NativeAd nativeAd;
    NativeAdListener nativeAdListener;
    private NativeBannerAd nativeBannerAd;

    public void loadNativeAd(Context context, String id, NativeAdLayout nativeAdLayout, int frameLayout) {
        nativeAd = new NativeAd(context, id);
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                Log.e("TAG fb", "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("TAG fb", "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e("TAG fb", "Native ad is loaded and ready to be displayed!");
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                inflateAd(context, nativeAdLayout, nativeAd, frameLayout);
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.d("TAG fb", "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.d("TAG fb", "Native ad impression logged!");
            }
        };
        PrefManager prefManager = new PrefManager(context);
        if (!prefManager.getBooleanExtra("adsPurchase") || !prefManager.getBooleanExtra("fullPurchase")) {
            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        }
    }

    public void inflateAd(Context context, NativeAdLayout nativeAdLayout, NativeAd nativeAd, int frameLayout) {
        nativeAd.unregisterView();
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout ladView = (LinearLayout) inflater.inflate(frameLayout, nativeAdLayout, false);
        nativeAdLayout.addView(ladView);
        LinearLayout adChoicesContainer = ladView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(context, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);
        MediaView nativeAdIcon = ladView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = ladView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = ladView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = ladView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = ladView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = ladView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = ladView.findViewById(R.id.native_ad_call_to_action);
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeAd.registerViewForInteraction(ladView, nativeAdMedia, nativeAdIcon, clickableViews);
    }

    public void loadBannerAd(Context context, String id, LinearLayout adContainer) {
        AdView adView = new AdView(context, id, AdSize.BANNER_HEIGHT_50);
        adContainer.addView(adView);
        adView.loadAd();
    }

    public void loadFbNativeBanner(Context context, NativeAdLayout nativeAdLayout, String id) {
        nativeBannerAd = new NativeBannerAd(context, id);
        nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeBannerAd == null || nativeBannerAd != ad) {
                    return;
                }
                inflateNativeBannerAd(context, nativeAdLayout, nativeBannerAd);
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        };
        PrefManager prefManager = new PrefManager(context);
        if (!prefManager.getBooleanExtra("adsPurchase") || !prefManager.getBooleanExtra("fullPurchase")) {
            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        }
    }


    private void inflateNativeBannerAd(Context context, NativeAdLayout nativeAdLayout, NativeBannerAd nativeBannerAd) {
        nativeBannerAd.unregisterView();
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.fb_native_banner, nativeAdLayout, false);
        nativeAdLayout.addView(adView);
        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(context, nativeBannerAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        MediaView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);
        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
    }

}


