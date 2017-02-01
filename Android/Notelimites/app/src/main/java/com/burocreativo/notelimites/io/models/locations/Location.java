
package com.burocreativo.notelimites.io.models.locations;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("locationID")
    private Long mLocationID;
    @SerializedName("locationLat")
    private String mLocationLat;
    @SerializedName("locationLng")
    private String mLocationLng;
    @SerializedName("locationName")
    private String mLocationName;
    @SerializedName("locationSlug")
    private String mLocationSlug;

    public Long getLocationID() {
        return mLocationID;
    }

    public void setLocationID(Long locationID) {
        mLocationID = locationID;
    }

    public String getLocationLat() {
        return mLocationLat;
    }

    public void setLocationLat(String locationLat) {
        mLocationLat = locationLat;
    }

    public String getLocationLng() {
        return mLocationLng;
    }

    public void setLocationLng(String locationLng) {
        mLocationLng = locationLng;
    }

    public String getLocationName() {
        return mLocationName;
    }

    public void setLocationName(String locationName) {
        mLocationName = locationName;
    }

    public String getLocationSlug() {
        return mLocationSlug;
    }

    public void setLocationSlug(String locationSlug) {
        mLocationSlug = locationSlug;
    }

}
