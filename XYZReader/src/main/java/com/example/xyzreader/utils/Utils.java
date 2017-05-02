package com.example.xyzreader.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by manvi on 2/5/17.
 */

public final class Utils {


    public static boolean isNetworkConnectionAvailable(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork!=null && activeNetwork.isConnectedOrConnecting())
        {
            return true;
        }
        else{
            return false;
        }
    }
}
