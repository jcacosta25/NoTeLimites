
package com.burocreativo.notelimites.io.models.locations;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Locations {

    @SerializedName("locations")
    private List<Location> mLocations;

    public List<Location> getLocations() {
        return mLocations;
    }

    public void setLocations(List<Location> locations) {
        mLocations = locations;
    }

}
