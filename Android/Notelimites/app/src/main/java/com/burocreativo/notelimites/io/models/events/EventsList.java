package com.burocreativo.notelimites.io.models.events;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventsList {

  @SerializedName("events")
  @Expose
  private List<Event> events;
  @SerializedName("categories")
  @Expose
  private List<Category> categories;
  @SerializedName("venues")
  @Expose
  private List<Venues> venues;

  public void setEvents(List<Event> events) {
    this.events = events;
  }

  public List<Event> getEvents() {
    return events;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public List<Venues> getVenues() {
    return venues;
  }

  public void setVenues(List<Venues> venues) {
    this.venues = venues;
  }
}