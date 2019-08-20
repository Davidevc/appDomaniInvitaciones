package com.example.domanisistemainvitaciones.utilitarios;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Conectividad {

    private static ConnectivityManager manager;


    public static boolean isNetowrkProvider(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() &&

                networkInfo.isConnected();
    }
}
