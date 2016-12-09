package com.burocreativo.notelimites.io.models.places;

import com.burocreativo.notelimites.io.models.events.EventsList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Venue {

	@SerializedName("venueName")
	@Expose
	private String venueName;

	@SerializedName("address")
	@Expose
	private String address;

	@SerializedName("venueUID")
	@Expose
	private String venueUID;

	@SerializedName("lng")
	@Expose
	private String lng;

	@SerializedName("city")
	@Expose
	private String city;

	@SerializedName("venueID")
	@Expose
	private int venueID;

	@SerializedName("imageURL")
	@Expose
	private String imageURL;

	@SerializedName("description")
	@Expose
	private Object description;

	@SerializedName("venueURLID")
	@Expose
	private Object venueURLID;

	@SerializedName("slug")
	@Expose
	private String slug;

	@SerializedName("lat")
	@Expose
	private String lat;

	@SerializedName("events")
	@Expose
	private List<EventsList> events;

	public void setVenueName(String venueName){
		this.venueName = venueName;
	}

	public String getVenueName(){
		return venueName;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setVenueUID(String venueUID){
		this.venueUID = venueUID;
	}

	public String getVenueUID(){
		return venueUID;
	}

	public void setLng(String lng){
		this.lng = lng;
	}

	public String getLng(){
		return lng;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setVenueID(int venueID){
		this.venueID = venueID;
	}

	public int getVenueID(){
		return venueID;
	}

	public void setImageURL(String imageURL){
		this.imageURL = imageURL;
	}

	public String getImageURL(){
		return imageURL;
	}

	public void setDescription(Object description){
		this.description = description;
	}

	public Object getDescription(){
		return description;
	}

	public void setVenueURLID(Object venueURLID){
		this.venueURLID = venueURLID;
	}

	public Object getVenueURLID(){
		return venueURLID;
	}

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	public void setEvents(List<EventsList> events){
		this.events = events;
	}

	public List<EventsList> getEvents(){
		return events;
	}
}