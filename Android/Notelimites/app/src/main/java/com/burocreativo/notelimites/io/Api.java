package com.burocreativo.notelimites.io;

import com.burocreativo.notelimites.io.models.events.Event;
import com.burocreativo.notelimites.io.models.events.EventsList;
import com.burocreativo.notelimites.io.models.events.Location;
import com.burocreativo.notelimites.io.models.token.SignInResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Juan C. Acosta on 10/25/2016.
 * bbxmstudios
 * juank2acosta@gmail.com
 */
public interface Api {

    @GET("/sign_in")
    Call<SignInResult> signin(@Query("email") String email, @Query("password") String password);

    @POST("/locations")
    Call<EventsList> getEventLocations(@Header("Authorization") String token, @Body Location location);

    @GET("/events/{eventid}")
    Call<Event> getEvent(@Header("Authorization") String token, @Path("eventid") String eventid);
}