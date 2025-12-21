package com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.LocationServiceCode;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.intruderselfie.geolocation.hiddenpic.thirdeye.MainCode.Utils.PrefManager;

public class LocationHelper {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Context context;

    public LocationHelper(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Handle the location update
                // You can use the 'location' object to get latitude and longitude
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                PrefManager.browserURL = "12";
                Log.e("Get", " 12" + latitude + longitude);
                // Stop listening for location updates once received

                PrefManager.browserURL = "12" + latitude + longitude;
                stopLocationUpdates();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
    }

    public void requestSingleLocationUpdate() {
        // Use the Fused Location Provider to request a single location update
        // The last parameter is the Looper, you can pass null to use the current thread
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);
    }

    private void stopLocationUpdates() {
        // Stop listening for location updates
        locationManager.removeUpdates(locationListener);
    }
}
