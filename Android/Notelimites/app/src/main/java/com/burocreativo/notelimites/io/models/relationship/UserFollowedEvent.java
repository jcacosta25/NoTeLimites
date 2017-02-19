
package com.burocreativo.notelimites.io.models.relationship;

import com.burocreativo.notelimites.io.models.events.Event;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserFollowedEvent {

    @SerializedName("relationEvents")
    private List<Event> mRelationEvents;

    public List<Event> getRelationEvents() {
        return mRelationEvents;
    }

    public void setRelationEvents(List<Event> relationEvents) {
        mRelationEvents = relationEvents;
    }

}
