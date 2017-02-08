package com.burocreativo.notelimites;

import android.app.Application;

import com.burocreativo.notelimites.io.ServiceGenerator;
import com.facebook.FacebookSdk;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Juan C. Acosta on 8/22/2016.
 * juancacosta25@gmail.com.com
 */
public class NTLApplication  extends Application{

    public static GoogleApiClient googleApiClient;
    @Override
    public void onCreate() {
        super.onCreate();
        //FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
        ServiceGenerator.authToken = "i2gShFXzWnLF2A7f8_aQ";
    }
}
