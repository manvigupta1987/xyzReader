package com.example.xyzreader.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.xyzreader.data.ArticleLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import timber.log.Timber;

/**
 * Created by manvi on 2/5/17.
 */

public final class Utils {

    public static String TRANSITION_STRING = "transition";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
    // Use default locale format
    public static SimpleDateFormat outputFormat = new SimpleDateFormat();
    // Most time functions can only handle 1902 - 2037
    public static GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2,1,1);

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

    public static Date parsePublishedDate(String date) {
        try {

            return dateFormat.parse(date);
        } catch (ParseException ex) {
            Timber.e(ex, ex.getMessage());
            Timber.i("passing today's date");
            return new Date();
        }
    }
}
