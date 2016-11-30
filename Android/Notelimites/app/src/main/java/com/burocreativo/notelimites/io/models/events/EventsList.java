package com.burocreativo.notelimites.io.models.events;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventsList{

	@SerializedName("events")
	@Expose
	private List<Event> events;

	public void setEvents(List<Event> events){
		this.events = events;
	}

	public List<Event> getEvents(){
		return events;
	}
}