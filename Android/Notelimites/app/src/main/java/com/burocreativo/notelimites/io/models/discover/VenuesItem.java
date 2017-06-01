package com.burocreativo.notelimites.io.models.discover;

import com.google.gson.annotations.SerializedName;

public class VenuesItem{

	@SerializedName("venueName")
	private String venueName;

	@SerializedName("venueLng")
	private String venueLng;

	@SerializedName("venueUID")
	private String venueUID;

	@SerializedName("venueLat")
	private String venueLat;

	@SerializedName("venueSlug")
	private String venueSlug;

	@SerializedName("venueID")
	private int venueID;

	@SerializedName("url")
	private String url;

	public void setVenueName(String venueName){
		this.venueName = venueName;
	}

	public String getVenueName(){
		return venueName;
	}

	public void setVenueLng(String venueLng){
		this.venueLng = venueLng;
	}

	public String getVenueLng(){
		return venueLng;
	}

	public void setVenueUID(String venueUID){
		this.venueUID = venueUID;
	}

	public String getVenueUID(){
		return venueUID;
	}

	public void setVenueLat(String venueLat){
		this.venueLat = venueLat;
	}

	public String getVenueLat(){
		return venueLat;
	}

	public void setVenueSlug(String venueSlug){
		this.venueSlug = venueSlug;
	}

	public String getVenueSlug(){
		return venueSlug;
	}

	public void setVenueID(int venueID){
		this.venueID = venueID;
	}

	public int getVenueID(){
		return venueID;
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
			"VenuesItem{" + 
			"venueName = '" + venueName + '\'' + 
			",venueLng = '" + venueLng + '\'' + 
			",venueUID = '" + venueUID + '\'' + 
			",venueLat = '" + venueLat + '\'' + 
			",venueSlug = '" + venueSlug + '\'' + 
			",venueID = '" + venueID + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}