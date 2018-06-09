package com.bala.sample.view.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bala.sample.R;
import com.bala.sample.model.util.Customer_model;
import com.bala.sample.model.util.StartLocationAlert;
import com.bala.sample.presenter.Customer_presenter;
import com.bala.sample.view.Mvp.Customer_mvp;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Muthu on 06/08/18.
 */

public class InsertActivity extends AppCompatActivity implements Customer_mvp.MainView, View.OnClickListener, LocationListener, OnMapReadyCallback {

    Customer_presenter customerPresenter;
    public static ProgressDialog dialog;
    EditText name, phone, location;
    Button save;
    String lat, longi;
    FusedLocationProviderClient fusedLocationProviderClient;
    GoogleMap mMap;

    Location locationn;
    LatLng latLng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location locations) {
                        locationn = locations;
                        latLng=new LatLng(locations.getLatitude(),locations.getLongitude());
//                        Toast.makeText(InsertActivity.this, "bala"+locations.getLatitude(), Toast.LENGTH_SHORT).show();
                        mMap.addMarker(new MarkerOptions().position(latLng).icon(
                                BitmapDescriptorFactory.defaultMarker()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                        // Zoom in, animating the camera.
                        mMap.animateCamera(CameraUpdateFactory.zoomIn());
                        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);



                        longi = String.valueOf(locations.getLongitude());
                        lat = String.valueOf(locations.getLatitude());

                        Log.d("location", String.valueOf(locations.getLatitude()));
                        Log.d("location", String.valueOf(locations.getLongitude()));

                        // Got last known location. In some rare situations this can be null.
                        if (locations != null) {
                            // Logic to handle location object
                        }
                    }
                });

        customerPresenter = new Customer_presenter(this);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        location = (EditText) findViewById(R.id.location);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);

        dialog = new ProgressDialog(InsertActivity.this);
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);


        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void setError(String error) {

    }

    @Override
    public void setDeleteSuccess(String message) {
        dialog.dismiss();
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), dataActivity.class));
        finish();
    }

    @Override
    public void setAdapterData(ArrayList<Customer_model> data) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        StartLocationAlert startLocationAlert = new StartLocationAlert(InsertActivity.this);
    }

    @Override
    public void onClick(View view) {
        if (view == save) {
            String name_param = name.getText().toString().trim();
            String phone_param = phone.getText().toString().trim();
            String location_param = location.getText().toString().trim();

            if (name_param.length() == 0 && phone_param.length() == 0 && location_param.length() == 0) {
                Toast.makeText(this, "fill all data", Toast.LENGTH_SHORT).show();
            } else if (phone_param.length() != 10) {
                Toast.makeText(this, "Enter valid Phone Number", Toast.LENGTH_SHORT).show();
            } else {
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("Customer_Name", name_param);
                    jsonObject.put("Customer_MobileNumber", phone_param);
                    jsonObject.put("Beat", location_param);
                    jsonObject.put("Latittude", lat);
                    jsonObject.put("Longitude", longi);
                    customerPresenter.add(jsonObject);

//                    dialog = new ProgressDialog(getApplicationContext());
//                    dialog.setMessage("please wait...");
//                    dialog.setCancelable(false);
                    dialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();
        locationn = location;
        latLng=new LatLng(location.getLatitude(),location.getLongitude());
//                        Toast.makeText(InsertActivity.this, "bala"+locations.getLatitude(), Toast.LENGTH_SHORT).show();
        mMap.addMarker(new MarkerOptions().position(latLng).icon(
                BitmapDescriptorFactory.defaultMarker()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        longi = String.valueOf(location.getLongitude());
        lat = String.valueOf(location.getLatitude());
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }

    }
}
