package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.IntruderLocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.LocationDatabase.LocationDatabase;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.LocationDatabase.LocationEntity;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

import java.util.ArrayList;
import java.util.List;

public class SavedIntruderLocations extends AppCompatActivity {

    List<LocationEntity> arrayList;
    SavedLocationAdapter adapter;
    LocationDatabase locationDatabase;
    RecyclerView recyclerView;
    TextView textView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_intruder_locations);
        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        textView = findViewById(R.id.noIntruder);
        locationDatabase = LocationDatabase.getDatabase(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SavedLocationAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
        getLocations();

    }

    void getLocations() {


        if (ContextCompat.checkSelfPermission(SavedIntruderLocations.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            arrayList = locationDatabase.locationDao().getAllLocations();
            adapter.setList(arrayList);
            if (arrayList.isEmpty()) {
                Log.e("Empty", "::::::");
                findViewById(R.id.noIntruder).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.noIntruder).setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
        } else {
            ActivityCompat.requestPermissions(SavedIntruderLocations.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                arrayList = locationDatabase.locationDao().getAllLocations();
                adapter.setList(arrayList);
                if (arrayList.isEmpty()) {
                    Log.e("Empty", "::::::");
                    findViewById(R.id.noIntruder).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.noIntruder).setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            } else {
                textView.setVisibility(View.VISIBLE);
                textView.setText("Location permission is not given, please allow it from setting.");
                Toast.makeText(this, "This functionality won't work without location permission", Toast.LENGTH_SHORT).show();

            }
        }
    }
}