package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.IntruderSavedImages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC.Utility;

import java.io.File;
import java.util.ArrayList;

public class IntruderImagesAdapter extends RecyclerView.Adapter<IntruderImagesAdapter.ViewHolder> {
    Activity activity;
    ArrayList<ImagesModel> arrayList;

    public IntruderImagesAdapter(Activity activity, ArrayList<ImagesModel> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_view_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTheView(activity, arrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemImageView);
        }

        void bindTheView(Context activity, ImagesModel imagesModel) {
            Glide.with(activity)
                    .load(Uri.fromFile(new File(String.valueOf(imagesModel.getImagesPath()))))
                    .into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utility.imagePath = String.valueOf(imagesModel.getImagesPath());
                    activity.startActivity(new Intent(activity, ViewIntrudersActivity.class));
                }
            });
        }
    }
}
