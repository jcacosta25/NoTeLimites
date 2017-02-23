
package com.burocreativo.notelimites.io.models.places;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.burocreativo.notelimites.BR;
import com.burocreativo.notelimites.io.models.events.Event;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Venue extends BaseObservable {
    @SerializedName("address")
	@Expose
	@Bindable
    private String mAddress;
    @SerializedName("city")
	@Expose
	@Bindable
    private String mCity;
    @SerializedName("description")
	@Expose
	@Bindable
    private String mDescription;
    @SerializedName("events")
	@Expose
	@Bindable
    private List<Event> mEvents;
    @SerializedName("followed")
	@Expose
	@Bindable
    private boolean mFollowed;
    @SerializedName("imageURL")
	@Expose
	@Bindable
    private String mImageURL;
    @SerializedName("lat")
	@Expose
	@Bindable
    private String mLat;
    @SerializedName("lng")
	@Expose
	@Bindable
    private String mLng;
    @SerializedName("slug")
	@Expose
	@Bindable
    private String mSlug;
    @SerializedName("venueID")
	@Expose
	@Bindable
    private Long mVenueID;
    @SerializedName("venueName")
	@Expose
	@Bindable
    private String mVenueName;
    @SerializedName("venueUID")
	@Expose
	@Bindable
    private String mVenueUID;
    @SerializedName("venueURLID")
	@Expose
	@Bindable
    private String mVenueURLID;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
        notifyPropertyChanged(BR.address);
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
        notifyPropertyChanged(BR.city);
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
        notifyPropertyChanged(BR.description);
    }

    public List<Event> getEvents() {
        return mEvents;
    }

    public void setEvents(List<Event> events) {
        mEvents = events;
        notifyPropertyChanged(BR.events);
    }

    public boolean getFollowed() {
        return mFollowed;
    }

    public void setFollowed(boolean followed) {
        mFollowed = followed;
        notifyPropertyChanged(BR.followed);
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
        notifyPropertyChanged(BR.imageURL);
    }

    public String getLat() {
        return mLat;
    }

    public void setLat(String lat) {
        mLat = lat;
        notifyPropertyChanged(BR.lat);
    }

    public String getLng() {
        return mLng;
    }

    public void setLng(String lng) {
        mLng = lng;
        notifyPropertyChanged(BR.lng);
    }

    public String getSlug() {
        return mSlug;
    }

    public void setSlug(String slug) {
        mSlug = slug;
        notifyPropertyChanged(BR.slug);
    }

    public Long getVenueID() {
        return mVenueID;
    }

    public void setVenueID(Long venueID) {
        mVenueID = venueID;
        notifyPropertyChanged(BR.venueID);
    }

    public String getVenueName() {
        return mVenueName;
    }

    public void setVenueName(String venueName) {
        mVenueName = venueName;
        notifyPropertyChanged(BR.venueName);
    }

    public String getVenueUID() {
        return mVenueUID;
    }

    public void setVenueUID(String venueUID) {
        mVenueUID = venueUID;
        notifyPropertyChanged(BR.venueUID);
    }

    public String getVenueURLID() {
        return mVenueURLID;
    }

    public void setVenueURLID(String venueURLID) {
        mVenueURLID = venueURLID;
        notifyPropertyChanged(BR.venueURLID);
    }

}
