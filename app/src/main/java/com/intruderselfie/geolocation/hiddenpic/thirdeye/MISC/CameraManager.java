package com.intruderselfie.geolocation.hiddenpic.thirdeye.MISC;

import static com.ammarptn.gdriverest.DriveServiceHelper.getGoogleDriveService;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.ammarptn.gdriverest.DriveServiceHelper;
import com.ammarptn.gdriverest.GoogleDriveFileHolder;

import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class CameraManager implements Camera.PictureCallback, Camera.ErrorCallback, Camera.PreviewCallback, Camera.AutoFocusCallback {
    private static CameraManager mManager;
    private Camera mCamera;
    public Context mContext;
    private SurfaceTexture mSurface;
    PrefManager prefManager;
    double getLatitude = 0;
    double getLongitude = 0;

    public CameraManager(Context context) {
        this.mContext = context;
        this.prefManager = new PrefManager(context);
    }

    public static CameraManager getInstance(Context context) {
        if (mManager == null) {
            mManager = new CameraManager(context);
        }


        return mManager;
    }

    public void takePhoto() {
        if (isFrontCameraAvailable()) {
            initCamera();
        }
    }

    private boolean isFrontCameraAvailable() {
        Camera.CameraInfo cameraInfo = null;
        Context context = this.mContext;
        if (context == null || !context.getPackageManager().hasSystemFeature("android.hardware.camera")) {
            return false;
        }
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            try {
                cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            if (cameraInfo.facing == 1) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("StaticFieldLeak")
    private void initCamera() {
        new AsyncTask<Void, Void, Camera>() {

            @Override
            protected Camera doInBackground(Void... voids) {
                Camera camera = null;
                try {
                    camera = Camera.open(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return camera;
            }

            @Override
            protected void onPostExecute(Camera camera) {
                if (camera != null) {
                    try {
                        CameraManager.this.mCamera = camera;
                        CameraManager.this.setupCamera();
                    } catch (IOException e) {
                        e.printStackTrace();
                        CameraManager.this.releaseCamera();
                    }
                } else {
                }
            }
        }.execute();
    }

    private void setupCamera() throws IOException {
        CameraManager.this.mSurface = new SurfaceTexture(123);
        CameraManager.this.mCamera.setPreviewTexture(CameraManager.this.mSurface);

        Camera.Parameters parameters = CameraManager.this.mCamera.getParameters();
        parameters.setRotation(270);

        if (autoFocusSupported(CameraManager.this.mCamera)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }

        CameraManager.this.mCamera.setParameters(parameters);
        CameraManager.this.mCamera.setPreviewCallback(CameraManager.this);
        CameraManager.this.mCamera.setErrorCallback(CameraManager.this);
        CameraManager.this.mCamera.startPreview();
    }

    private boolean autoFocusSupported(Camera camera) {
        List<String> supportedFocusModes = camera.getParameters().getSupportedFocusModes();
        return supportedFocusModes != null && supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    public void releaseCamera() {
        Camera camera = this.mCamera;
        if (camera != null) {
            camera.release();
            this.mSurface.release();
            this.mCamera = null;
            this.mSurface = null;
        }
    }

    @Override
    public void onError(int i, Camera camera) {

    }

    @Override
    public void onPreviewFrame(byte[] bArr, Camera camera) {
        try {
            camera.setPreviewCallback(null);
            camera.takePicture(null, null, this);
        } catch (Exception e) {
            Log.e("ContentValues", "Camera error while taking picture");
            e.printStackTrace();
            releaseCamera();
        }
    }

    @Override
    public void onAutoFocus(boolean z, Camera camera) {
        if (camera != null) {
            try {
                camera.takePicture(null, null, this);
                this.mCamera.autoFocus(null);
            } catch (Exception e) {
                e.printStackTrace();
                releaseCamera();
            }
        }
    }

    @Override
    public void onPictureTaken(byte[] bArr, Camera camera) {
        savePicture(bArr, mContext);
        releaseCamera();
    }

    private void savePicture(byte[] data, Context context) {
        if (context == null) {
            Log.e("Conte", "Context Null");
            return;
        }
        if (data == null || data.length == 0) {
            Log.e("CameraManager", "Can't save image - invalid data");
            return;
        }

        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String fileName = "IntruderCapture_" + timeStamp + ".jpg";

            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/" + mContext.getString(R.string.app_name));

            if (!downloadsDir.exists() && !downloadsDir.mkdirs()) {
                Log.e("CameraManager", "Can't create Downloads directory to save image.");
                return;
            }

            File file = new File(downloadsDir, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();

            Log.d("CameraManager", "Image saved last original: " + file.getPath());
            file.getAbsolutePath();


            /*TODO Google Drive Logic Here*/


            if (prefManager.getBooleanExtra("photoSync")){
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
                if (account != null) {
                    DriveServiceHelper mDriveServiceHelper = new DriveServiceHelper(getGoogleDriveService(context, account, context.getString(R.string.app_name)));
                    uploadImage(mDriveServiceHelper, fileName);
                }else{
                    Log.e("Empty", "No Account");
                }
            }else{
                Log.e("Not Activated", "No False");
            }


        } catch (IOException e) {
            Log.e("CameraManager", "Error saving image: " + e);
            e.printStackTrace();
        }
    }


    public void uploadImage(DriveServiceHelper mDriveServiceHelper, String imageName) {

        //TODO Change App Name

        Log.e("Get File", ": " + new java.io.File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toURI() + "/Intruder Detector/" + imageName));

        mDriveServiceHelper.uploadFile(new java.io.File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Intruder Detector/", imageName), "image/*", prefManager.getStringExtra("driveFolder"))
                .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                    @Override
                    public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
                        Gson gson = new Gson();

                        Log.d("TAG", "onSuccess: " + gson.toJson(googleDriveFileHolder));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                    }
                });
    }




}