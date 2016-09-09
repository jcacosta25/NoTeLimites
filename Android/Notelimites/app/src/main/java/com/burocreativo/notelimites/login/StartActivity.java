package com.burocreativo.notelimites.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.home.HomeActivity;
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

public class StartActivity extends FragmentActivity {

    private CallbackManager callbackManager;
    private LoginButton fb_login_button;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    View layout;
    private static final int REQUEST_LOCATION = 0;
    public static final String TAG = "LOGIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        layout = findViewById(R.id.view);
        callbackManager = CallbackManager.Factory.create();
        fb_login_button = (LoginButton) findViewById(R.id.login_button_fb);
        fb_login_button.setReadPermissions(Arrays.asList("public_profile,email,user_hometown,user_likes,user_status,user_about_me,user_location,user_tagged_places,user_birthday"));


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        Intent intent = new Intent(this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void showPreview(View view) {
        Log.i(TAG, "Show camera button pressed. Checking permission.");
        // BEGIN_INCLUDE(camera_permission)
        // Check if the Camera permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Camera permission has not been granted.

            requestPermission();

        } else {

            // Camera permissions is already available, show the camera preview.
            Log.i(TAG,
                    " permission has already been granted. Displaying camera preview.");
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

        }
        // END_INCLUDE(camera_permission)

    }


    private void requestPermission() {
        Log.i(TAG, "Location permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i(TAG,
                    "Displaying camera permission rationale to provide additional context.");
            Snackbar.make(layout, R.string.permission_location_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(StartActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    REQUEST_LOCATION);
                        }
                    })
                    .show();
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
        // END_INCLUDE(camera_permission_request)
    }
}
