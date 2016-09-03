package com.burocreativo.notelimites.login;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.burocreativo.notelimites.R;
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

public class HomeActivity extends FragmentActivity {

    private CallbackManager callbackManager;
    private LoginButton fb_login_button;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    public static final String TAG = "LOGIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        fb_login_button = (LoginButton) findViewById(R.id.login_button_fb);
        fb_login_button.setReadPermissions(Arrays.asList("public_profile,email,user_hometown,user_likes,user_status,user_about_me,user_location,user_tagged_places,user_birthday"));
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

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };


    }
}
