package com.burocreativo.notelimites.io.models.discover;

/**
 * Created by Juan C. Acosta on 5/25/2017.
 */

public class Discover {

  int id = 0;
  String type = "event";
  String title = "";
  String lat = "";
  String lng = "";

  public Discover(String type,int id, String title, String lat, String lng) {
    this.type = type;
    this.id = id;
    this.title = title;
    this.lat = lat;
    this.lng = lng;
  }

  public Discover(int id, String title) {
    this.id = id;
    this.title = title;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLat() {
    return lat;
  }

  public void setLat(String lat) {
    this.lat = lat;
  }

  public String getLng() {
    return lng;
  }

  public void setLng(String lng) {
    this.lng = lng;
  }
}
