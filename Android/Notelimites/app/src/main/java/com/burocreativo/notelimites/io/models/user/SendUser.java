package com.burocreativo.notelimites.io.models.user;

/**
 * Created by Juan C. Acosta on 2/16/2017.
 */

public class SendUser {

    private String email;
    private String oauth_token;
    private String name;

    public SendUser(String email, String oauth_token, String name) {
        this.email = email;
        this.oauth_token = oauth_token;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOauth_token() {
        return oauth_token;
    }

    public void setOauth_token(String oauth_token) {
        this.oauth_token = oauth_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
