package com.burocreativo.notelimites.io;

import com.burocreativo.notelimites.io.models.token.SignInResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Juan C. Acosta on 10/25/2016.
 * bbxmstudios
 * juan.acosta@bbxmstudios.com
 */
public interface Api {

    @GET("/sign_in")
    Call<SignInResult> signin(@Query("email") String email, @Query("password") String password);
}
