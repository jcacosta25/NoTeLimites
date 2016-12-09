package com.burocreativo.notelimites;

import android.app.Application;

import com.burocreativo.notelimites.io.ServiceGenerator;
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
        //FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
        ServiceGenerator.authToken = "i2gShFXzWnLF2A7f8_aQ";
    }
}
