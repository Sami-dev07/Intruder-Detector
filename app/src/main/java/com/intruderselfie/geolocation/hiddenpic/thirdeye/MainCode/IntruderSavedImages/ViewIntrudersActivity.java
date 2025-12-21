package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.IntruderSavedImages;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC.Utility;

import java.io.File;

public class ViewIntrudersActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_intruders);
        imageView = findViewById(R.id.imageView);
        Glide.with(this)
                .load(Uri.fromFile(new File(String.valueOf(Utility.imagePath))))
                .into(imageView);
    }
}