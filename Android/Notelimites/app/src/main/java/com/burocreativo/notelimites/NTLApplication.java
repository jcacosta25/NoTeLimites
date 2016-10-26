package com.burocreativo.notelimites;

import android.app.Application;

import com.burocreativo.notelimites.io.ServiceGenerator;
import com.burocreativo.notelimites.io.models.token.SignInResult;
import com.facebook.FacebookSdk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Call<SignInResult> call = ServiceGenerator.getApiService().signin("manuel@notelimites.com","manumanu");
        call.enqueue(new Callback<SignInResult>() {
            @Override
            public void onResponse(Call<SignInResult> call, Response<SignInResult> response) {
                if(response.isSuccessful()) {
                    ServiceGenerator.authToken = response.body().getAuthToken();
                }
            }

            @Override
            public void onFailure(Call<SignInResult> call, Throwable t) {

            }
        });
    }
}
