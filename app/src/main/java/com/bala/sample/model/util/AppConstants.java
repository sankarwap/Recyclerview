package com.bala.sample.model.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by CDS124 on 25-04-2018.
 */

public class AppConstants {


    public static final String LOGIN_SESSION_NAME = "login_session";
    public static final String LOGIN_SESSION_USER_ID = "_id";
    public static final String LOGIN_SESSION_USER_NAME = "name";
    public static final String LOGIN_SESSION_USER_MOBILE = "mobileNo";


    public static final String[] ALL_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static final int ALL_REQUEST_CODE = 0;
    public static void turnGpsOn(final Context context) {
        final AppPermissions mRuntimePermission = new AppPermissions((AppCompatActivity) context);
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            mRuntimePermission.requestPermission(AppConstants.ALL_PERMISSIONS, AppConstants.ALL_REQUEST_CODE);
        }
        else {
            Toast.makeText(context, "Gps enabled", Toast.LENGTH_SHORT).show();
        }
        return;
    }

}
