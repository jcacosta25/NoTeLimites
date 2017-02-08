package com.burocreativo.notelimites.io.models;

/**
 * Created by Juan C. Acosta on 9/4/2016.
 * juancacosta25@gmail.com.com
 */
public class Page {
    private String id;
    private String url;

    public Page() {
    }

    public Page(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
