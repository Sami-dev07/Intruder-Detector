package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.OnBoardingScreens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.NativeAdView;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.MainUi;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.newBoarding.LanguageActivity;

public class CloudOnBoardScreen extends AppCompatActivity {

    Button cloud_next;

    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_on_board_screen);
        /*NativeAdView nativeAdView = new NativeAdView();
        nativeAdView.loadNative(this);*/
        prefManager = new PrefManager(this);

        /*findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager.addToBoolean("onBoard", true);
                startActivity(new Intent(CloudOnBoardScreen.this, MainUi.class));
            }
        });*/

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.tap_animation);
        cloud_next = findViewById(R.id.mContinue);







        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //findViewById(R.id.progress).setVisibility(View.INVISIBLE);
                cloud_next.setVisibility(View.VISIBLE);
            }
        }, 100);














        cloud_next.setOnClickListener(new View.OnClickListener() {

























            @Override
            public void onClick(View view) {
                cloud_next.startAnimation(animation);
                prefManager.addToBoolean("onBoard", true);
                startActivity(new Intent(CloudOnBoardScreen.this, LanguageActivity.class));
            }
        });
    }
}