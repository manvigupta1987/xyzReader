package com.example.xyzreader;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by manvi on 2/5/17.
 */

public class xyzReader extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());
        }
    }
}
