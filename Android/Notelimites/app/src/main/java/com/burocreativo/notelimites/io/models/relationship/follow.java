package com.burocreativo.notelimites.io.models.relationship;

/**
 * Created by Juan C. Acosta on 2/7/2017.
 * juancacosta25@gmail.com
 */

public class follow {

    private String followerId;
    private String followedId;

    public follow(String followerId, String followedId) {
        this.followerId = followerId;
        this.followedId = followedId;
    }
}
