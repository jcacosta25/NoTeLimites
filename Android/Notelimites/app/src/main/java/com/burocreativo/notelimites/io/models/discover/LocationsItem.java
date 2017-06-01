package com.burocreativo.notelimites.io.models.discover;

import com.google.gson.annotations.SerializedName;

public class LocationsItem{

	@SerializedName("locationLat")
	private String locationLat;

	@SerializedName("locationName")
	private String locationName;

	@SerializedName("locationID")
	private int locationID;

	@SerializedName("locationSlug")
	private String locationSlug;

	@SerializedName("locationLng")
	private String locationLng;

	public void setLocationLat(String locationLat){
		this.locationLat = locationLat;
	}

	public String getLocationLat(){
		return locationLat;
	}

	public void setLocationName(String locationName){
		this.locationName = locationName;
	}

	public String getLocationName(){
		return locationName;
	}

	public void setLocationID(int locationID){
		this.locationID = locationID;
	}

	public int getLocationID(){
		return locationID;
	}

	public void setLocationSlug(String locationSlug){
		this.locationSlug = locationSlug;
	}

	public String getLocationSlug(){
		return locationSlug;
	}

	public void setLocationLng(String locationLng){
		this.locationLng = locationLng;
	}

	public String getLocationLng(){
		return locationLng;
	}

	@Override
 	public String toString(){
		return 
			"LocationsItem{" + 
			"locationLat = '" + locationLat + '\'' + 
			",locationName = '" + locationName + '\'' + 
			",locationID = '" + locationID + '\'' + 
			",locationSlug = '" + locationSlug + '\'' + 
			",locationLng = '" + locationLng + '\'' + 
			"}";
		}
}