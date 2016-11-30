
package com.burocreativo.notelimites.io.models.token;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SignInResult {

    @SerializedName("auth_token")
    private String mAuthToken;
    @SerializedName("email")
    private String mEmail;

    public String getAuthToken() {
        return mAuthToken;
    }

    public void setAuthToken(String auth_token) {
        mAuthToken = auth_token;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

}
