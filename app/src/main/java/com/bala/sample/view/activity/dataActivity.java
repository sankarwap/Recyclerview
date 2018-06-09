package com.bala.sample.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bala.sample.R;
import com.bala.sample.model.util.AppConstants;
import com.bala.sample.model.util.AppPermissions;
import com.bala.sample.model.util.Customer_model;
import com.bala.sample.model.util.StartLocationAlert;
import com.bala.sample.presenter.Customer_presenter;
import com.bala.sample.view.Mvp.Customer_mvp;
import com.bala.sample.view.adapter.CustomerAdapter;

import java.util.ArrayList;

public class dataActivity extends AppCompatActivity implements Customer_mvp.MainView, LocationListener {
    CustomerAdapter customerAdapter;
    AppPermissions mRuntimePermission;
    ArrayList<Customer_model> data;
    public static ProgressDialog dialog;
    public static Customer_presenter customer_presenter;
    RecyclerView recyclerView;
    FloatingActionButton add;
    Location location;

    LocationManager locationManager;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customer_presenter = new Customer_presenter(this);
        data = new ArrayList<>();
        customer_presenter.loadData();
        context=dataActivity.this;
        mRuntimePermission = new AppPermissions(this);

        if (mRuntimePermission.hasPermission(AppConstants.ALL_PERMISSIONS)) {
            // Toast.makeText(this, "All permission already given", Toast.LENGTH_SHORT).show();
            Log.d("Success", "All permission already given");
        } else {
            mRuntimePermission.requestPermission(AppConstants.ALL_PERMISSIONS, AppConstants.ALL_REQUEST_CODE);
        }
        locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }


        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Enable Location");
            dialog.setCancelable(false);
            dialog.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    AppConstants.turnGpsOn(dataActivity.this);
                }
            });
            dialog.show();
        }

        dialog = new ProgressDialog(dataActivity.this);
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
        dialog.show();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        add = (FloatingActionButton) findViewById(R.id.add);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customerAdapter = new CustomerAdapter(getApplicationContext(), data);
        recyclerView.setAdapter(customerAdapter);
        recyclerView.setHasFixedSize(true);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InsertActivity.class));
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        StartLocationAlert startLocationAlert = new StartLocationAlert(dataActivity.this);
    }

    @Override
    public void setError(String error) {
        dialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDeleteSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//        customer_presenter.loadData();
        startActivity(new Intent(getApplicationContext(), dataActivity.class));
        finish();
    }

    @Override
    public void setAdapterData(ArrayList<Customer_model> datas) {
        dialog.dismiss();
        data.addAll(datas);
        customerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLocationChanged(Location location) {

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


}
