package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.IntruderLocation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.LocationDatabase.LocationEntity;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

import java.util.Collections;
import java.util.List;

public class SavedLocationAdapter extends RecyclerView.Adapter<SavedLocationAdapter.ViewHolder> {

    Context context;
    List<LocationEntity> arraylist;

    public SavedLocationAdapter(Context context, List<LocationEntity> arraylist) {
        this.context = context;
        this.arraylist = arraylist;
    }
    public void setList(List<LocationEntity> list){
        this.arraylist = list;
        Collections.reverse(this.arraylist);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_location, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.timeStamp.setText(arraylist.get(position).timestamp);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mapUrl = arraylist.get(position).description;
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
                context.startActivity(webIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeStamp;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            timeStamp = itemView.findViewById(R.id.timeStamp);
        }
    }
}
