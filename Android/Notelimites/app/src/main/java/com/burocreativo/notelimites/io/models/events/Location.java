package com.burocreativo.notelimites.io.models.events;

public class Location {
	private String auth_token;
	private String lng;
	private String lat;

	public Location(String auth_token,String lat,String lng) {
		this.auth_token = auth_token;
		this.lat = lat;
		this.lng = lng;
	}
}
