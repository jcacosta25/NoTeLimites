package com.burocreativo.notelimites.io.models.events;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.burocreativo.notelimites.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VenueEvents extends BaseObservable {

	@SerializedName("eventId")
	@Expose
	@Bindable
	private int eventId;

	@SerializedName("imageURL")
	@Expose
	@Bindable
	private String imageURL;

	@SerializedName("eventName")
	@Expose
	@Bindable
	private String eventName;

	@SerializedName("eventSlug")
	@Expose
	@Bindable
	private String eventSlug;

	public void setEventId(int eventId){
		this.eventId = eventId;
		notifyPropertyChanged(BR.eventId);
	}

	public int getEventId(){
		return eventId;
	}

	public void setImageURL(String imageURL){
		this.imageURL = imageURL;
		notifyPropertyChanged(BR.imageURL);
	}

	public String getImageURL(){
		return imageURL;
	}

	public void setEventName(String eventName){
		this.eventName = eventName;
		notifyPropertyChanged(BR.eventName);
	}

	public String getEventName(){
		return eventName;
	}

	public void setEventSlug(String eventSlug){
		this.eventSlug = eventSlug;
		notifyPropertyChanged(BR.eventSlug);
	}

	public String getEventSlug(){
		return eventSlug;
	}
}