package com.burocreativo.notelimites;

import android.app.Application;

import com.burocreativo.notelimites.io.ServiceGenerator;
import com.burocreativo.notelimites.utils.TinyDB;
import com.facebook.FacebookSdk;
import com.google.android.gms.common.api.GoogleApiClient;

import io.branch.referral.Branch;

/**
 * Created by Juan C. Acosta on 8/22/2016.
 * juancacosta25@gmail.com.com
 */
public class NTLApplication  extends Application{

    public static GoogleApiClient googleApiClient;
    public static TinyDB tinyDB;
    @Override
    public void onCreate() {
        super.onCreate();
        //FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
        ServiceGenerator.authToken = "i2gShFXzWnLF2A7f8_aQ";
        // initialize the Branch object
        Branch.getAutoInstance(this);
        tinyDB = new TinyDB(this);

    }
}
