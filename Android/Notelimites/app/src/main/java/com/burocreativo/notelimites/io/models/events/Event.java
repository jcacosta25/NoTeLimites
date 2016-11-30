package com.burocreativo.notelimites.io.models.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

	@SerializedName("end_date")
	@Expose
	private String endDate;

	@SerializedName("init_date")
	@Expose
	private String initDate;

	@SerializedName("eventID")
	@Expose
	private int eventID;

	@SerializedName("eventUID")
	@Expose
	private String eventUID;

	@SerializedName("ticketMaster")
	@Expose
	private Object ticketMaster;

	@SerializedName("description")
	@Expose
	private String description;

	@SerializedName("attendings")
	@Expose
	private int attendings;

	@SerializedName("placeLng")
	@Expose
	private String placeLng;

	@SerializedName("placeLat")
	@Expose
	private String placeLat;

	@SerializedName("venueID")
	@Expose
	private int venueID;

	@SerializedName("imageURL")
	@Expose
	private String imageURL;

	@SerializedName("eventtypeID")
	@Expose
	private int eventtypeID;

	@SerializedName("eventName")
	@Expose
	private String eventName;

	@SerializedName("ranking")
	@Expose
	private Object ranking;

	@SerializedName("eventURLID")
	@Expose
	private Object eventURLID;

	public void setEndDate(String endDate){
		this.endDate = endDate;
	}

	public String getEndDate(){
		return endDate;
	}

	public void setInitDate(String initDate){
		this.initDate = initDate;
	}

	public String getInitDate(){
		return initDate;
	}

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

	public void setTicketMaster(Object ticketMaster){
		this.ticketMaster = ticketMaster;
	}

	public Object getTicketMaster(){
		return ticketMaster;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setAttendings(int attendings){
		this.attendings = attendings;
	}

	public int getAttendings(){
		return attendings;
	}

	public void setPlaceLng(String placeLng){
		this.placeLng = placeLng;
	}

	public String getPlaceLng(){
		return placeLng;
	}

	public void setPlaceLat(String placeLat){
		this.placeLat = placeLat;
	}

	public String getPlaceLat(){
		return placeLat;
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

	public void setEventtypeID(int eventtypeID){
		this.eventtypeID = eventtypeID;
	}

	public int getEventtypeID(){
		return eventtypeID;
	}

	public void setEventName(String eventName){
		this.eventName = eventName;
	}

	public String getEventName(){
		return eventName;
	}

	public void setRanking(Object ranking){
		this.ranking = ranking;
	}

	public Object getRanking(){
		return ranking;
	}

	public void setEventURLID(Object eventURLID){
		this.eventURLID = eventURLID;
	}

	public Object getEventURLID(){
		return eventURLID;
	}
}