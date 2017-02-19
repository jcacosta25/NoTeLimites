
package com.burocreativo.notelimites.io.models.relationship;

import com.google.gson.annotations.SerializedName;


public class EventFollowed {

    @SerializedName("followed")
    private Boolean mFollowed;

    public Boolean getFollowed() {
        return mFollowed;
    }

    public void setFollowed(Boolean followed) {
        mFollowed = followed;
    }

}
