package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.OnBoardingScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.MainUi;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

public class SmSOnBoardActivity extends AppCompatActivity {

    CardView sms_next;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm_son_board);

        prefManager = new PrefManager(this);

        findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager.addToBoolean("onBoard", true);
                startActivity(new Intent(SmSOnBoardActivity.this, MainUi.class));
            }
        });

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.tap_animation);
        sms_next = findViewById(R.id.sms_next);
        sms_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager.addToBoolean("onBoard", true);
                sms_next.startAnimation(animation);
                startActivity(new Intent(SmSOnBoardActivity.this, CloudOnBoardScreen.class));
            }
        });


    }
}