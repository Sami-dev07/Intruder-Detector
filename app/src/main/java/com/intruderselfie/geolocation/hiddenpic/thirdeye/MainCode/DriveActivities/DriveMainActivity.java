package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.DriveActivities;

import static com.ammarptn.gdriverest.DriveServiceHelper.getGoogleDriveService;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ammarptn.gdriverest.DriveServiceHelper;
import com.ammarptn.gdriverest.GoogleDriveFileHolder;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.AdsClasses.NativeAdView;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.InAppPurchase;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager;
import com.intruderselfie.geolocation.hiddenpic.thirdeye.R;

public class DriveMainActivity extends AppCompatActivity {

    TextView connectedTextView, getEmailTextView;
    ImageView connect_drive_ui_BTN;
    LabeledSwitch driveLocationSyncSwitch, drivePhotoSyncSwitch;
    DriveServiceHelper mDriveServiceHelper;
    GoogleSignInAccount account;
    private final int REQUEST_CODE_SIGN_IN = 100;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private static final int BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE = 124;
    CardView photoCardView, locationCardView;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_main);
        account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        connectedTextView = findViewById(R.id.connectedTextView);
        prefManager = new PrefManager(this);
        getEmailTextView = findViewById(R.id.getEmailTextView);
        connect_drive_ui_BTN = findViewById(R.id.connect_drive_ui_BTN);
        drivePhotoSyncSwitch = findViewById(R.id.drivePhotoSyncSwitch);
        driveLocationSyncSwitch = findViewById(R.id.driveLocationSyncSwitch);
        locationCardView = findViewById(R.id.locationCardView);
        photoCardView = findViewById(R.id.photoCardView);

        NativeAdView nativeAdView = new NativeAdView();
        nativeAdView.loadNativeSplash(this);
        if (prefManager.getBooleanExtra("drivePurchase") || prefManager.getBooleanExtra("fullPurchase")) {
            driveLocationSyncSwitch.setEnabled(true);
            drivePhotoSyncSwitch.setEnabled(true);
            drivePhotoSyncSwitch.setOnToggledListener(new OnToggledListener() {
                @Override
                public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                    if (isOn) {
                        prefManager.addToBoolean("photoSync", true);
                    } else {
                        prefManager.addToBoolean("photoSync", false);
                    }
                }
            });
            if (ContextCompat.checkSelfPermission(DriveMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                driveLocationSyncSwitch.setOnToggledListener(new OnToggledListener() {
                    @Override
                    public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                        if (isOn) {
                            prefManager.addToBoolean("locationSync", true);
                        } else {
                            prefManager.addToBoolean("locationSync", false);
                        }
                    }
                });
            } else {
                ActivityCompat.requestPermissions(DriveMainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            driveLocationSyncSwitch.setEnabled(false);
            drivePhotoSyncSwitch.setEnabled(false);
        }
        locationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!driveLocationSyncSwitch.isEnabled()) {
                    Toast.makeText(DriveMainActivity.this, "Please Purchase First. Start Activity", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DriveMainActivity.this, InAppPurchase.class));
                }
            }
        });
        photoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!drivePhotoSyncSwitch.isEnabled()) {
                    Toast.makeText(DriveMainActivity.this, "Please Purchase First. Start Activity", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DriveMainActivity.this, InAppPurchase.class));
                }
            }
        });
        connect_drive_ui_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                if (account == null) {
                    signIn();
                } else {
                    signOut();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        driveLocationSyncSwitch.setOn(prefManager.getBooleanExtra("locationSync"));
        drivePhotoSyncSwitch.setOn(prefManager.getBooleanExtra("photoSync"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (account != null) {
            signIn();
        } else {
            getEmailTextView.setText("Connected to: N/A");
            getEmailTextView.setTextColor(getColor(R.color.red));
            connectedTextView.setTextColor(getColor(R.color.red));
            connectedTextView.setText("Disconnected");
            connect_drive_ui_BTN.setImageResource(R.drawable.connect_drive_ui);
        }
    }

    private void signIn() {
        GoogleSignInClient mGoogleSignInClient = buildGoogleSignInClient();
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }

    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .requestEmail()
                        .build();
        return GoogleSignIn.getClient(getApplicationContext(), signInOptions);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode == Activity.RESULT_OK && resultData != null) {
                    handleSignInResult(resultData);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, resultData);
    }

    private void handleSignInResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        Log.e("asdasdadasTAG Google SignIn", "Signed in as " + googleSignInAccount.getEmail());
                        getEmailTextView.setText("Connected to: " + googleSignInAccount.getEmail());
                        getEmailTextView.setTextColor(getColor(R.color.green));
                        connectedTextView.setTextColor(getColor(R.color.green));
                        connectedTextView.setText("Connected");
                        mDriveServiceHelper = new DriveServiceHelper(getGoogleDriveService(getApplicationContext(), googleSignInAccount, getString(R.string.app_name)));
                        connect_drive_ui_BTN.setImageResource(R.drawable.drive_disconnect);
                        mDriveServiceHelper.createFolderIfNotExist(getString(R.string.app_name), null)
                                .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                                    @Override
                                    public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
                                        Gson gson = new Gson();
                                        prefManager.addToString("driveFolder", googleDriveFileHolder.getId());
                                        Log.e("asdasdasdaTAG", "onSuccess: " + gson.toJson(googleDriveFileHolder));
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("asdasdasdaTAG", "onFailure: " + e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("sadasdasTAG", "Unable to sign in.", e);
                        Toast.makeText(DriveMainActivity.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void signOut() {
        GoogleSignInClient mGoogleSignInClient = buildGoogleSignInClient();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            getEmailTextView.setText("Connected to: N/A");
                            getEmailTextView.setTextColor(getColor(R.color.red));
                            connectedTextView.setTextColor(getColor(R.color.red));
                            connectedTextView.setText("Disconnected");
                            connect_drive_ui_BTN.setImageResource(R.drawable.connect_drive_ui);
                        } else {
                            Toast.makeText(DriveMainActivity.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    driveLocationSyncSwitch.setOnToggledListener(new OnToggledListener() {
                        @Override
                        public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                            if (isOn) {
                                prefManager.addToBoolean("locationSync", true);
                            } else {
                                prefManager.addToBoolean("locationSync", false);
                            }
                        }
                    });
                }
            }
        }
    }

}