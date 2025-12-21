package com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC;

import static com.ammarptn.gdriverest.DriveServiceHelper.getGoogleDriveService;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ammarptn.gdriverest.DriveServiceHelper;
import com.ammarptn.gdriverest.GoogleDriveFileHolder;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.LocationDatabase.LocationDatabase;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.LocationDatabase.LocationEntity;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PasswordReceiver extends DeviceAdminReceiver {

    PrefManager prefManager;

    @Override
    public void onEnabled(@NonNull Context context, @NonNull Intent intent) {

    }

    @Override
    public void onPasswordChanged(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle user) {
        super.onPasswordChanged(context, intent, user);
    }

    @Override
    public void onPasswordSucceeded(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle user) {
        super.onPasswordSucceeded(context, intent, user);
        Log.e("User", " " + user);
        PrefManager.attempts = 1;

    }

    @Override
    public void onPasswordFailed(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle userHandle) {
        super.onPasswordFailed(context, intent, userHandle);
        LocationDatabase locationDatabase = LocationDatabase.getDatabase(context);

        prefManager = new PrefManager(context);
        if (PrefManager.attempts >= prefManager.getIntegerExtra("wrongAttempts")) {
            CameraManager.getInstance(context).takePhoto();
            String timeStamp1 = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss", Locale.getDefault()).format(new Date());


           if (Utility.longitude != 0 && Utility.latitude != 0){
               Log.e("Added To database", "Added");
               LocationEntity locationEntity = new LocationEntity();
               locationEntity.title = "Intruder Detected";
               locationEntity.description = "https://maps.google.com/?q=" + Utility.latitude + "," + Utility.longitude;
               locationEntity.timestamp = timeStamp1;
               locationDatabase.locationDao().insert(locationEntity);
               String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
               String fileName = "IntruderCapture_" + timeStamp;

               if (prefManager.getBooleanExtra("locationSync")) {
                   Log.e("CameraMasadasdsanager", "Lcoatino Synte tereu ");
                   GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
                   if (account != null) {
                       Log.e("12112", "212112 false location ");
                       DriveServiceHelper mDriveServiceHelper = new DriveServiceHelper(getGoogleDriveService(context, account, context.getString(R.string.app_name)));
                       uploadLocationData(mDriveServiceHelper, fileName, Utility.latitude, Utility.longitude, timeStamp1);

                   } else {
                       Log.e("12112", "212112 falseasdasdasdasdasdasdasdasd location ");
                   }


               }
           }else{
               Log.e("Location: ", "Null: ");
           }


        } else {
            Log.e("Cant Take Photo", "Because: " + PrefManager.attempts + "Less Than" + prefManager.getIntegerExtra("wrongAttempts"));
            PrefManager.attempts++;
        }

    }

    @Override
    public CharSequence onDisableRequested(Context context, @NonNull Intent intent) {
        return "Admin rights are being requested to be disabled for the app called: '" + context.getString(R.string.app_name) + "'.";
    }

    @Override
    public void onDisabled(@NonNull Context context, @NonNull Intent intent) {

    }

    private void uploadLocationData(DriveServiceHelper driveServiceHelper, String name, double latitude, double longitude, String timeStamp) {
        driveServiceHelper.createTextFile(name + ".txt", "Intruder Detected At " + timeStamp + "\nIntruder location is : " + "https://maps.google.com/?q=" + latitude + "," + longitude, prefManager.getStringExtra("driveFolder"))
                .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                    @Override
                    public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
                        Gson gson = new Gson();
                        Log.e(" Location Tag", "onSuccess: " + gson.toJson(googleDriveFileHolder));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG Location", "onFailure: " + e.getMessage());
                    }
                });
    }

}