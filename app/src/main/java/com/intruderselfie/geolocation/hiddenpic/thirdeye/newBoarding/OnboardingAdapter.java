package com.intruderselfie.geolocation.hiddenpic.thirdeye.newBoarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;


public class OnboardingAdapter extends PagerAdapter {

    private Context context;
    private int[] layouts = {
            R.layout.layout1,
            R.layout.layout2,
            R.layout.layout3

    };

    OnboardingAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return false;
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layouts[position], container, false);
        view.setTag(position);




//        ImageView imageView = view.findViewById(R.id.areaGif);;

//        Glide.with(context)
//                .load(R.drawable.area_gif)
//                .into(imageView);
//        ImageView imageView1 = view.findViewById(R.id.areaGif);
//        Glide.with(context)
//                .load(R.drawable.distance_gif)
//                .into(imageView1);



        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
//        container.removeView((ConstraintLayout) object);
    }
}
