package com.burocreativo.notelimites.io.models.events;

public class Data {
	private String auth_token;
	private String lng;
	private String lat;

	public Data(String auth_token, String lat, String lng) {
		this.auth_token = auth_token;
		this.lat = lat;
		this.lng = lng;
	}
}