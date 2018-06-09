package com.bala.sample.model.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.internal.ANRequestQueue;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by CDS123 on 26-04-2018.
 */


public class AppController extends Application implements Application.ActivityLifecycleCallbacks {

    private static AppController mInstance;



    @Override
    public void onCreate() {

        super.onCreate();


        mInstance = this;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        AndroidNetworking.initialize(mInstance, okHttpClient);




    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public void clearQueue(String tag) {
        ANRequestQueue.getInstance().cancelRequestWithGivenTag(tag, false);
        Log.w("Success", "" + tag + " Queue Cleared");
    }

    public void clearAllQueue() {
        ANRequestQueue.getInstance().cancelAll(false);
        Log.w("Success", "All Queue Cleared");
    }



    public static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {


    }


}
