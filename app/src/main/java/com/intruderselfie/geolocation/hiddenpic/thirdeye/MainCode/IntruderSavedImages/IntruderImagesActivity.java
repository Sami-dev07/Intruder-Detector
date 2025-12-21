package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.IntruderSavedImages;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class IntruderImagesActivity extends AppCompatActivity {

    private ArrayList<ImagesModel> arrayList;
    private IntruderImagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intruder_images);

        arrayList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new IntruderImagesAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (!Environment.isExternalStorageManager()) {
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
//                intent.setData(uri);
//                startActivity(intent);
//            }
            loadCursorImages();
        } else {
            loadLegacyImages();
        }





        checkIntruder();
    }

    void loadLegacyImages() {

        File basePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File baseFolder = new File(basePath, getString(R.string.app_name));
        if (baseFolder.exists() && baseFolder.isDirectory()) {
            File[] images = baseFolder.listFiles();
            if (images != null) {
                for (File file : images) {
                    if (file.isFile()) {
                        String imagePath = file.getAbsolutePath();
                        ImagesModel imagesModel = new ImagesModel();
                        imagesModel.setImagesPath(Uri.parse(imagePath));
                        arrayList.add(imagesModel);

                    }
                }
                Collections.reverse(arrayList);
            }
        }

        adapter.notifyDataSetChanged();

    }

    void loadCursorImages() {
        File basePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File baseFolder = new File(basePath, getString(R.string.app_name));

        if (baseFolder.exists() && baseFolder.isDirectory()) {
            String folderPath = baseFolder.getAbsolutePath();
            String selection = MediaStore.Images.Media.DATA + " LIKE ?";
            String[] selectionARGS = new String[]{folderPath + "/%"};

            ContentResolver contentResolver = getContentResolver();
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor = contentResolver.query(uri, null, selection, selectionARGS, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    ImagesModel imagesModel = new ImagesModel();
                    imagesModel.setImagesPath(Uri.parse(imagePath));
                    arrayList.add(imagesModel);
                } while (cursor.moveToNext());
                cursor.close();
                Collections.reverse(arrayList);
                adapter.notifyDataSetChanged();
            }
        }
    }


    void checkIntruder(){
        if (arrayList.isEmpty()){
            findViewById(R.id.noIntruder).setVisibility(View.VISIBLE);
            findViewById(R.id.recyclerView).setVisibility(View.INVISIBLE);
        }else{
            findViewById(R.id.noIntruder).setVisibility(View.INVISIBLE);
            findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);
        }
    }

}