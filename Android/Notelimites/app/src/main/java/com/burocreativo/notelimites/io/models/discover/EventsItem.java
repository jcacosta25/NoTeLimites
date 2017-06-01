package com.burocreativo.notelimites.io.models.discover;

import com.google.gson.annotations.SerializedName;

public class EventsItem{

	@SerializedName("eventID")
	private int eventID;

	@SerializedName("eventUID")
	private String eventUID;

	@SerializedName("eventName")
	private String eventName;

	@SerializedName("eventSlug")
	private String eventSlug;

	@SerializedName("followed")
	private String followed;

	@SerializedName("url")
	private String url;

	public void setEventID(int eventID){
		this.eventID = eventID;
	}

	public int getEventID(){
		return eventID;
	}

	public void setEventUID(String eventUID){
		this.eventUID = eventUID;
	}

	public String getEventUID(){
		return eventUID;
	}

	public void setEventName(String eventName){
		this.eventName = eventName;
	}

	public String getEventName(){
		return eventName;
	}

	public void setEventSlug(String eventSlug){
		this.eventSlug = eventSlug;
	}

	public String getEventSlug(){
		return eventSlug;
	}

	public void setFollowed(String followed){
		this.followed = followed;
	}

	public String getFollowed(){
		return followed;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"EventsItem{" + 
			"eventID = '" + eventID + '\'' + 
			",eventUID = '" + eventUID + '\'' + 
			",eventName = '" + eventName + '\'' + 
			",eventSlug = '" + eventSlug + '\'' + 
			",followed = '" + followed + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}