package com.burocreativo.notelimites.io;

import com.burocreativo.notelimites.io.models.events.Data;
import com.burocreativo.notelimites.io.models.events.Event;
import com.burocreativo.notelimites.io.models.events.EventsList;
import com.burocreativo.notelimites.io.models.locations.Locations;
import com.burocreativo.notelimites.io.models.token.SignInResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Juan C. Acosta on 10/25/2016.
 * juank2acosta@gmail.com
 */
public interface Api {

    @GET("sign_in")
    Call<SignInResult> signin(@Query("email") String email, @Query("password") String password);

    @Headers("Content-Type: application/json")
    @POST("locations/")
    Call<EventsList> getEventLocations(@Body Data locations);

    @GET("locations/?auth_token=i2gShFXzWnLF2A7f8_aQ")
    Call<Locations> getLocations();

    @GET("events/{eventid}")
    Call<Event> getEvent(@Path("eventid") String eventid, @Query("auth_token") String token);
}
