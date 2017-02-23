package com.burocreativo.notelimites.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.burocreativo.notelimites.NTLApplication;
import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.ServiceGenerator;
import com.burocreativo.notelimites.io.models.user.SendUser;
import com.burocreativo.notelimites.io.models.user.UserResponse;
import com.burocreativo.notelimites.screens.profile.ProfileActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends FragmentActivity {

    private CallbackManager callbackManager;
    private LoginButton fb_login_button;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    View layout;
    public static final String TAG = "LOGIN";
    private boolean profile = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        layout = findViewById(R.id.view);
        callbackManager = CallbackManager.Factory.create();
        fb_login_button = (LoginButton) findViewById(R.id.login_button_fb);
        profile = getIntent().getBooleanExtra("profile",false);
        fb_login_button.setReadPermissions(Arrays.asList("public_profile,email,user_hometown,user_likes,user_about_me,user_location,user_birthday"));


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };

        fb_login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.d(TAG, "onCompleted: Login Completed " + String.valueOf(response));
                                Log.d(TAG, "onCompleted jsonObject: " + object);
                                Log.d(TAG, "onCompleted response: " + response);
                                Log.e(TAG, "onCompleted: Accestoken" + AccessToken.getCurrentAccessToken().getToken());
                                accessToken = AccessToken.getCurrentAccessToken();
                                // Application code

                                String userId = object.optString("id");
                                SendUser user = new SendUser(object.optString("email"),String.valueOf(accessToken),object.optString("name"));
                                Call<UserResponse> call = ServiceGenerator.getApiService().setUpUser(user);
                                call.enqueue(new Callback<UserResponse>() {
                                    @Override
                                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                        if(response.body().getImage() == null){
                                            response.body().setImage("https://graph.facebook.com/" + userId + "/picture?type=large");
                                        }
                                        NTLApplication.tinyDB.putObject("user",response.body());
                                        NTLApplication.tinyDB.putString("user_id",String.valueOf(response.body().getUserID()));
                                        if(profile){
                                            Intent intent = new Intent(StartActivity.this, ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            finish();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<UserResponse> call, Throwable t) {

                                    }
                                });
                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "onCancel: User Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "onError: " + error);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

}
