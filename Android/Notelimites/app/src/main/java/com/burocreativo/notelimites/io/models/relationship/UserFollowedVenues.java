
package com.burocreativo.notelimites.io.models.relationship;

import java.util.List;
import javax.annotation.Generated;

import com.burocreativo.notelimites.io.models.places.RelationVenue;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
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
