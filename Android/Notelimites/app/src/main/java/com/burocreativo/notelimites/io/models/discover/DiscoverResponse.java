package com.burocreativo.notelimites.io.models.discover;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DiscoverResponse{

	@SerializedName("locations")
	private List<LocationsItem> locations;

	@SerializedName("venues")
	private List<VenuesItem> venues;

	@SerializedName("events")
	private List<EventsItem> events;

	public void setLocations(List<LocationsItem> locations){
		this.locations = locations;
	}

	public List<LocationsItem> getLocations(){
		return locations;
	}

	public void setVenues(List<VenuesItem> venues){
		this.venues = venues;
	}

	public List<VenuesItem> getVenues(){
		return venues;
	}

	public void setEvents(List<EventsItem> events){
		this.events = events;
	}

	public List<EventsItem> getEvents(){
		return events;
	}

	@Override
 	public String toString(){
		return 
			"DiscoverResponse{" + 
			"locations = '" + locations + '\'' + 
			",venues = '" + venues + '\'' + 
			",events = '" + events + '\'' + 
			"}";
		}
}