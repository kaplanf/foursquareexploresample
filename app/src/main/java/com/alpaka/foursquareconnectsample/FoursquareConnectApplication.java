package com.alpaka.foursquareconnectsample;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.alpaka.foursquareconnectsample.location.LocationManager;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Fatih Kaplan on 28/04/16.
 */
public class FoursquareConnectApplication extends Application {

    private static FoursquareConnectApplication singleton;

    public static String FQ_APP_SECRET = "ZCXISNHTRMHYNXVUIW0V1GHNJGQTGVK0HACTI0FXURH2QVAL";
    public static String FQ_APP_ID = "YKGRTBHBU5LJZ1WWY40J4HS0FPEHDXAM5RU40SITXHBZ2ZLT";

    public static Picasso p;


    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        LocationManager.initializeManager(this);
        p = getPicassoInstance();
    }

    public static FoursquareConnectApplication getInstance() {
        if (singleton == null)
            singleton = new FoursquareConnectApplication();
        return singleton;
    }

    public String getDate() {
        Date date = new Date();
        DateFormat writeFormat = new SimpleDateFormat("yyyy:MM:dd");
        String formattedDate = writeFormat.format(date);
        formattedDate = formattedDate.replace(":", "");

        return formattedDate;
    }

    public Picasso getPicassoInstance() {
        if (p == null) {
            final int memClass = ((ActivityManager) getApplicationContext().getSystemService(
                    Context.ACTIVITY_SERVICE)).getMemoryClass();

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = 1024 * 1024 * memClass / 8;
            p = new Picasso.Builder(getApplicationContext())
                    .memoryCache(new LruCache(cacheSize))
                    .build();

        }
        return p;
    }
}
