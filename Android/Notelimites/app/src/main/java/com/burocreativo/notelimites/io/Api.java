package com.burocreativo.notelimites.io;

import com.burocreativo.notelimites.io.models.events.Data;
import com.burocreativo.notelimites.io.models.events.Event;
import com.burocreativo.notelimites.io.models.events.EventsList;
import com.burocreativo.notelimites.io.models.locations.Locations;
import com.burocreativo.notelimites.io.models.places.Venue;
import com.burocreativo.notelimites.io.models.relationship.EventFollowed;
import com.burocreativo.notelimites.io.models.relationship.Follow;
import com.burocreativo.notelimites.io.models.relationship.UserFollowedEvent;
import com.burocreativo.notelimites.io.models.relationship.UserFollowedVenues;
import com.burocreativo.notelimites.io.models.token.SignInResult;
import com.burocreativo.notelimites.io.models.user.SendUser;
import com.burocreativo.notelimites.io.models.user.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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
    @POST("users/?auth_token=i2gShFXzWnLF2A7f8_aQ")
    Call<UserResponse> setUpUser(@Body SendUser user);

    @Headers("Content-Type: application/json")
    @POST("locations/")
    Call<EventsList> getEventLocations(@Body Data locations,@Query("follower_id") String follower);

    @GET("locations/?auth_token=i2gShFXzWnLF2A7f8_aQ&tm=true")
    Call<Locations> getLocations();

    @GET("events/{eventid}")
    Call<Event> getEvent(@Path("eventid") String eventid, @Query("auth_token") String token,@Query("follower_id") String follower);

    @GET("venues/{venueid}")
    Call<Venue> getVenue(@Path("venueid") String eventid, @Query("auth_token") String token, @Query("follower_id") String follower);

    @HTTP(method = "DELETE", path = "relationevents?auth_token=i2gShFXzWnLF2A7f8_aQ", hasBody = true)
    Call<EventFollowed> unFollowEvent(@Body Follow follow);

    @POST("relationevents?auth_token=i2gShFXzWnLF2A7f8_aQ")
    Call<EventFollowed> followEvent(@Body Follow follow);

    @GET("relationevents?auth_token=i2gShFXzWnLF2A7f8_aQ")
    Call<UserFollowedEvent> userFollowedEvents(@Query("follower_id") String follower);


    @POST("relationvenues?auth_token=i2gShFXzWnLF2A7f8_aQ")
    Call<EventFollowed> followVenue(@Body Follow follow);

    @HTTP(method = "DELETE", path = "relationvenues?auth_token=i2gShFXzWnLF2A7f8_aQ", hasBody = true)
    Call<EventFollowed> unFollowVenue(@Body Follow follow);

    @GET("relationvenues?auth_token=i2gShFXzWnLF2A7f8_aQ")
    Call<UserFollowedVenues> userFollowedVenues(@Query("follower_id") String follower);

}
