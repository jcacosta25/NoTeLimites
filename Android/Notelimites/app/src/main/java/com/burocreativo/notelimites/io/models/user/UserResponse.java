
package com.burocreativo.notelimites.io.models.user;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("image")
    private String mImage;
    @SerializedName("name")
    private String mName;
    @SerializedName("sendEmail")
    private String mSendEmail;
    @SerializedName("slug")
    private String mSlug;
    @SerializedName("userID")
    private Long mUserID;
    @SerializedName("userUID")
    private Long mUserUID;
    @SerializedName("username")
    private String mUsername;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSendEmail() {
        return mSendEmail;
    }

    public void setSendEmail(String sendEmail) {
        mSendEmail = sendEmail;
    }

    public String getSlug() {
        return mSlug;
    }

    public void setSlug(String slug) {
        mSlug = slug;
    }

    public Long getUserID() {
        return mUserID;
    }

    public void setUserID(Long userID) {
        mUserID = userID;
    }

    public Long getUserUID() {
        return mUserUID;
    }

    public void setUserUID(Long userUID) {
        mUserUID = userUID;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

}
