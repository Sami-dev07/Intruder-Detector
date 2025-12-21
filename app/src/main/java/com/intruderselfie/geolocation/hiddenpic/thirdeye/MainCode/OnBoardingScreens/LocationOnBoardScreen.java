package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.OnBoardingScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.NativeAdView;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.MainUi;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

public class LocationOnBoardScreen extends AppCompatActivity {

    Button location_next;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_on_board_screen);
        /*NativeAdView nativeAdView = new NativeAdView();
        nativeAdView.loadNative(this);*/
        prefManager = new PrefManager(this);
       /* findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager.addToBoolean("onBoard", true);
                startActivity(new Intent(LocationOnBoardScreen.this, MainUi.class));
            }
        });*/






        location_next = findViewById(R.id.mContinue);







        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //findViewById(R.id.progress).setVisibility(View.INVISIBLE);
                location_next.setVisibility(View.VISIBLE);
            }
        }, 100);




        Animation animation = AnimationUtils.loadAnimation(this, R.anim.tap_animation);
        location_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager.addToBoolean("onBoard", true);
                location_next.startAnimation(animation);
                startActivity(new Intent(LocationOnBoardScreen.this, CloudOnBoardScreen.class ));
            }
        });

    }
}