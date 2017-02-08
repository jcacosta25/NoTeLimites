package com.burocreativo.notelimites.io.models;

/**
 * Created by Juan C. Acosta on 9/3/2016.

 * juancacosta25@gmail.com.com
 */
public class Event {

    private String date;
    private String name;
    private String place;
    private String people;

    public Event(String date, String name, String place, String people) {
        this.date = date;
        this.name = name;
        this.place = place;
        this.people = people;
    }

    public Event() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }
}
