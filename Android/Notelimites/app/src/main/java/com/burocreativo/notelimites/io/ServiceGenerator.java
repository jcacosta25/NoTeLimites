package com.burocreativo.notelimites.io;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Juan C. Acosta on 10/25/2016.
 * juank2acosta@gmail.com
 */
public class ServiceGenerator {
    private static  Api API_SERVICE;
    private static Retrofit retrofit;
    public static String authToken;
    public static Api getApiService(){
        String API_BASE_URL = "http://api.notelimites.com/v101/";
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API_SERVICE = retrofit.create(Api.class);
            return API_SERVICE;
    }
}

