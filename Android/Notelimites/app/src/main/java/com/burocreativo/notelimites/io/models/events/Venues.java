package com.burocreativo.notelimites.io.models.events;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Venues{

	@SerializedName("venueName")
	private String venueName;

	@SerializedName("venueLng")
	private String venueLng;

	@SerializedName("venueLat")
	private String venueLat;

	@SerializedName("venueID")
	private int venueID;

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

	public void setVenueLat(String venueLat){
		this.venueLat = venueLat;
	}

	public String getVenueLat(){
		return venueLat;
	}

	public void setVenueID(int venueID){
		this.venueID = venueID;
	}

	public int getVenueID(){
		return venueID;
	}

	@Override
 	public String toString(){
		return 
			"Venues{" + 
			"venueName = '" + venueName + '\'' + 
			",venueLng = '" + venueLng + '\'' + 
			",venueLat = '" + venueLat + '\'' + 
			",venueID = '" + venueID + '\'' + 
			"}";
		}
}