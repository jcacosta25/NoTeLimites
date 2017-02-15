package com.burocreativo.notelimites.io.models.events;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.burocreativo.notelimites.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Event extends BaseObservable {

    @SerializedName("end_date")
    @Expose
    @Bindable
    private Date endDate;
    @SerializedName("venueName")
    @Expose
    @Bindable
    private String venueName;
    @SerializedName("init_date")
    @Expose
    @Bindable
    private Date initDate;
    @SerializedName("eventID")
    @Expose
    @Bindable
    private int eventID;
    @SerializedName("eventUID")
    @Expose
    @Bindable
    private String eventUID;
    @SerializedName("ticketMaster")
    @Expose
    @Bindable
    private boolean ticketMaster;
    @SerializedName("description")
    @Expose
    @Bindable
    private String description;
    @SerializedName("attendings")
    @Expose
    @Bindable
    private int attendings;
    @SerializedName("placeLng")
    @Expose
    @Bindable
    private String placeLng;
    @SerializedName("placeLat")
    @Expose
    @Bindable
    private String placeLat;
    @SerializedName("venueID")
    @Expose
    @Bindable
    private int venueID;
    @SerializedName("imageURL")
    @Expose
    @Bindable
    private String imageURL;
    @SerializedName("eventtypeID")
    @Expose
    @Bindable
    private int eventtypeID;
    @SerializedName("eventName")
    @Expose
    @Bindable
    private String eventName;
    @SerializedName("ranking")
    @Expose
    @Bindable
    private Object ranking;
    @SerializedName("eventURLID")
    @Expose
    @Bindable
    private String eventURLID;

    @SerializedName("followed")
    @Expose
    @Bindable
    private String followed;

    @SerializedName("venueEvents")
    @Expose
    private List<VenueEvents> venueEvents;

    public Event() {
    }


    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
        notifyPropertyChanged(BR.venueName);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        notifyPropertyChanged(BR.endDate);
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
        notifyPropertyChanged(BR.initDate);
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
        notifyPropertyChanged(BR.eventID);
    }

    public String getEventUID() {
        return eventUID;
    }

    public void setEventUID(String eventUID) {
        this.eventUID = eventUID;
        notifyPropertyChanged(BR.eventUID);
    }

    public boolean getTicketMaster() {
        return ticketMaster;
    }

    public void setTicketMaster(boolean ticketMaster) {
        this.ticketMaster = ticketMaster;
        notifyPropertyChanged(BR.ticketMaster);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    public int getAttendings() {
        return attendings;
    }

    public void setAttendings(int attendings) {
        this.attendings = attendings;
        notifyPropertyChanged(BR.attendings);
    }

    public String getPlaceLng() {
        return placeLng;
    }

    public void setPlaceLng(String placeLng) {
        this.placeLng = placeLng;
        notifyPropertyChanged(BR.placeLng);
    }

    public String getPlaceLat() {
        return placeLat;
    }

    public void setPlaceLat(String placeLat) {
        this.placeLat = placeLat;
        notifyPropertyChanged(BR.placeLat);
    }

    public int getVenueID() {
        return venueID;
    }

    public void setVenueID(int venueID) {
        this.venueID = venueID;
        notifyPropertyChanged(BR.venueID);
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
        notifyPropertyChanged(BR.imageURL);
    }

    public int getEventtypeID() {
        return eventtypeID;
    }

    public void setEventtypeID(int eventtypeID) {
        this.eventtypeID = eventtypeID;
        notifyPropertyChanged(BR.eventtypeID);
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
        notifyPropertyChanged(BR.eventName);
    }

    public Object getRanking() {
        return ranking;
    }

    public void setRanking(Object ranking) {
        this.ranking = ranking;
        notifyPropertyChanged(BR.ranking);
    }

    public String getEventURLID() {
        return eventURLID;
    }

    public void setEventURLID(String eventURLID) {
        this.eventURLID = eventURLID;
        notifyPropertyChanged(BR.eventURLID);
    }

    public void setFollowed(String followed){
        this.followed = followed;
        notifyPropertyChanged(BR.followed);
    }

    public String getFollowed(){
        return followed;
    }

    public void setVenueEvents(List<VenueEvents> venueEvents){
        this.venueEvents = venueEvents;
    }

    public List<VenueEvents> getVenueEvents(){
        return venueEvents;
    }

}