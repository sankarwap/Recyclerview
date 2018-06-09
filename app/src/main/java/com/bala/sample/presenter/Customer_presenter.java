package com.bala.sample.presenter;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bala.sample.model.util.AppController;
import com.bala.sample.model.util.ConnectivityReceiver;
import com.bala.sample.model.util.Customer_model;
import com.bala.sample.view.Mvp.Customer_mvp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Muthu on 06/08/18.
 */

public class Customer_presenter implements Customer_mvp.MainPresenter {
    ArrayList<Customer_model> data = new ArrayList<>();

    private Customer_mvp.MainView v;
    private static final String TAG = "Error";

    public Customer_presenter(Customer_mvp.MainView view) {
        v = view;
    }


    @Override
    public void loadData() {


        if (ConnectivityReceiver.isConnected()) {
            AppController.getInstance().clearAllQueue();
            Log.e("inside", "inside ");
            final ANRequest request = AndroidNetworking.get("http://customerservice.dohrnii.com/api/Customers")
                    .setPriority(Priority.HIGH)
                    .build();
            Log.e("Success", "URL : " + request.getUrl());
            request.getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {


                        try {
                            if(response.getString("status").equals("Sucess")){

                            JSONArray jsonArray = response.getJSONArray("CustomerStatusList");

                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject = jsonArray.getJSONObject(j);
                                Customer_model loopProducts = new Customer_model();
//                                newTodos.add(new Todo(jsonObject.getLong("Id"), jsonObject.getString("Customer_Name"), jsonObject.getString("Beat")));
                                loopProducts.setId(jsonObject.getString("Id"));
                                loopProducts.setPhone(jsonObject.getString("Customer_MobileNumber"));
                                loopProducts.setName(jsonObject.getString("Customer_Name"));
                                loopProducts.setPlace(jsonObject.getString("Beat"));
                                loopProducts.setLat(jsonObject.getString("Latittude"));
                                loopProducts.setLongi(jsonObject.getString("Longitude"));
                                data.add(loopProducts);
                            }

                            v.setAdapterData(data);


                        }else {
                                v.setError("response error");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }

                @Override
                public void onError(ANError anError) {

                }

            });
        } else {
            v.setError("network Error");
        }
    }

    @Override
    public void delete(String id) {

        if (ConnectivityReceiver.isConnected()) {
            AppController.getInstance().clearAllQueue();
            Log.e("inside", "inside ");
            final ANRequest request = AndroidNetworking.delete("http://customerservice.dohrnii.com/api/Customers/"+id)
                    .setPriority(Priority.HIGH)
                    .build();
            Log.e("Success", "URL : " + request.getUrl());
            request.getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                      v.setDeleteSuccess(response.getString("Status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(ANError anError) {

                }

            });
        }

    }

    @Override
    public void add(JSONObject jsonObject) {

        if (ConnectivityReceiver.isConnected()) {
            AppController.getInstance().clearAllQueue();
            Log.e("inside", "inside ");
            final ANRequest request = AndroidNetworking.post("http://customerservice.dohrnii.com/api/Customers/")
                    .addJSONObjectBody(jsonObject)
                    .setPriority(Priority.HIGH)
                    .build();
            Log.e("Success", "URL : " + request.getUrl());
            request.getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                            v.setDeleteSuccess(response.getString("Status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(ANError anError) {

                }

            });
        }
    }

    @Override
    public void update(JSONObject jsonObject,String id) {
        if (ConnectivityReceiver.isConnected()) {
            AppController.getInstance().clearAllQueue();
            Log.e("inside", "inside ");
            final ANRequest request = AndroidNetworking.put("http://customerservice.dohrnii.com/api/Customers/"+id)
                    .addJSONObjectBody(jsonObject)
                    .setPriority(Priority.HIGH)
                    .build();
            Log.e("Success", "URL : " + request.getUrl());
            request.getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                         v.setDeleteSuccess(response.getString("Status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(ANError anError) {

                }

            });
        }

    }

}

