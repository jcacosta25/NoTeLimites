package com.burocreativo.notelimites.io.models.relationship;

/**
 * Created by Juan C. Acosta on 2/7/2017.
 * juancacosta25@gmail.com
 */

public class Follow {

    private String follower_id;
    private String followed_id;

    public Follow(String followerId, String followedId) {
        this.follower_id = followerId;
        this.followed_id = followedId;
    }
}
