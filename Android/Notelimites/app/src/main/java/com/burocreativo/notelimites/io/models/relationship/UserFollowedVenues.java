
package com.burocreativo.notelimites.io.models.relationship;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserFollowedVenues {

    @SerializedName("relationVenues")
    private List<RelationVenue> mRelationVenues;

    public List<RelationVenue> getRelationVenues() {
        return mRelationVenues;
    }

    public void setRelationVenues(List<RelationVenue> relationVenues) {
        mRelationVenues = relationVenues;
    }

}
