package com.burocreativo.notelimites.io.models.events;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Event implements Parcelable {

	@SerializedName("end_date")
	@Expose
	private Date endDate;

	@SerializedName("init_date")
	@Expose
	private Date initDate;

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

	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}

	public Date getEndDate(){
		return endDate;
	}

	public void setInitDate(Date initDate){
		this.initDate = initDate;
	}

	public Date getInitDate(){
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


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.endDate != null ? this.endDate.getTime() : -1);
		dest.writeLong(this.initDate != null ? this.initDate.getTime() : -1);
		dest.writeInt(this.eventID);
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

	public Event() {
	}

	protected Event(Parcel in) {
		long tmpEndDate = in.readLong();
		this.endDate = tmpEndDate == -1 ? null : new Date(tmpEndDate);
		long tmpInitDate = in.readLong();
		this.initDate = tmpInitDate == -1 ? null : new Date(tmpInitDate);
		this.eventID = in.readInt();
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
}