package com.burocreativo.notelimites;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by Juan C. Acosta on 8/22/2016.
 * bbxmstudios
 * juan.acosta@bbxmstudios.com
 */
public class NTLApplication  extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
