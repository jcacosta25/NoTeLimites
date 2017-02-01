package com.burocreativo.notelimites.io.models.events;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Event extends BaseObservable implements Parcelable {

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
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
    private Object ticketMaster;
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
    private Object eventURLID;

    public Event() {
    }

    private Event(Parcel in) {
        long tmpEndDate = in.readLong();
        this.endDate = tmpEndDate == -1 ? null : new Date(tmpEndDate);
        long tmpInitDate = in.readLong();
        this.initDate = tmpInitDate == -1 ? null : new Date(tmpInitDate);
        this.eventID = in.readInt();
        this.venueName = in.readString();
        this.eventUID = in.readString();
        this.ticketMaster = in.readParcelable(Object.class.getClassLoader());
        this.description = in.readString();
        this.attendings = in.readInt();
        this.placeLng = in.readString();
        this.placeLat = in.readString();
        this.venueID = in.readInt();
        this.imageURL = in.readString();
        this.eventtypeID = in.readInt();
        this.eventName = in.readString();
        this.ranking = in.readParcelable(Object.class.getClassLoader());
        this.eventURLID = in.readParcelable(Object.class.getClassLoader());
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.venueName);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.endDate);
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.initDate);
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.eventID);
    }

    public String getEventUID() {
        return eventUID;
    }

    public void setEventUID(String eventUID) {
        this.eventUID = eventUID;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.eventUID);
    }

    public Object getTicketMaster() {
        return ticketMaster;
    }

    public void setTicketMaster(Object ticketMaster) {
        this.ticketMaster = ticketMaster;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.ticketMaster);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.description);
    }

    public int getAttendings() {
        return attendings;
    }

    public void setAttendings(int attendings) {
        this.attendings = attendings;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.attendings);
    }

    public String getPlaceLng() {
        return placeLng;
    }

    public void setPlaceLng(String placeLng) {
        this.placeLng = placeLng;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.placeLng);
    }

    public String getPlaceLat() {
        return placeLat;
    }

    public void setPlaceLat(String placeLat) {
        this.placeLat = placeLat;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.placeLat);
    }

    public int getVenueID() {
        return venueID;
    }

    public void setVenueID(int venueID) {
        this.venueID = venueID;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.venueID);
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.imageURL);
    }

    public int getEventtypeID() {
        return eventtypeID;
    }

    public void setEventtypeID(int eventtypeID) {
        this.eventtypeID = eventtypeID;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.eventtypeID);
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.eventName);
    }

    public Object getRanking() {
        return ranking;
    }

    public void setRanking(Object ranking) {
        this.ranking = ranking;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.ranking);
    }

    public Object getEventURLID() {
        return eventURLID;
    }

    public void setEventURLID(Object eventURLID) {
        this.eventURLID = eventURLID;
        notifyPropertyChanged(com.burocreativo.notelimites.BR.eventURLID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.endDate != null ? this.endDate.getTime() : -1);
        dest.writeLong(this.initDate != null ? this.initDate.getTime() : -1);
        dest.writeInt(this.eventID);
        dest.writeString(this.venueName);
        dest.writeString(this.eventUID);
        dest.writeParcelable((Parcelable) this.ticketMaster, flags);
        dest.writeString(this.description);
        dest.writeInt(this.attendings);
        dest.writeString(this.placeLng);
        dest.writeString(this.placeLat);
        dest.writeInt(this.venueID);
        dest.writeString(this.imageURL);
        dest.writeInt(this.eventtypeID);
        dest.writeString(this.eventName);
        dest.writeParcelable((Parcelable) this.ranking, flags);
        dest.writeParcelable((Parcelable) this.eventURLID, flags);
    }
}