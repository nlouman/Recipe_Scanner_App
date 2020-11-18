package com.example.recipe_scanner_app.Main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;

public class CheckNetwork {
    Context context;

    // Passing context when creating the class
    public CheckNetwork(Context context) {
        this.context = context;
    }

    // Network Check
    public void registerNetworkCallback()
    {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();

            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
                                                                   @Override
                                                                   public void onAvailable(Network network) {
                                                                       GlobalVariables.isNetworkConnected = true; // Global Static Variable
                                                                   }
                                                                   @Override
                                                                   public void onLost(Network network) {
                                                                       GlobalVariables.isNetworkConnected = false; // Global Static Variable
                                                                   }
                                                               }

            );
            GlobalVariables.isNetworkConnected = false;
        }catch (Exception e){
            GlobalVariables.isNetworkConnected = false;
        }
    }
}
