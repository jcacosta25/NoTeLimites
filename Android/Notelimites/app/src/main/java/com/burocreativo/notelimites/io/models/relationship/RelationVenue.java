
package com.burocreativo.notelimites.io.models.relationship;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RelationVenue  extends BaseObservable{

    @SerializedName("description")
    @Expose
    @Bindable
    private String  mDescription;
    @SerializedName("followed")
    @Expose
    @Bindable
    private boolean mFollowed;
    @SerializedName("imageURL")
    @Expose
    @Bindable
    private String mImageURL;
    @SerializedName("placeLat")
    @Expose
    @Bindable
    private String mPlaceLat;
    @SerializedName("placeLng")
    @Expose
    @Bindable
    private String mPlaceLng;
    @SerializedName("relationVenueID")
    @Expose
    @Bindable
    private Long mRelationVenueID;
    @SerializedName("venueID")
    @Expose
    @Bindable
    private Long mVenueID;
    @SerializedName("venueName")
    @Expose
    @Bindable
    private String mVenueName;
    @SerializedName("venueSlug")
    @Expose
    @Bindable
    private String mVenueSlug;
    @SerializedName("venueUID")
    @Expose
    @Bindable
    private String mVenueUID;
    @SerializedName("venueURLID")
    @Expose
    @Bindable
    private String mVenueURLID;

    public Object getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
        notifyPropertyChanged(BR.description);
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

    public String getPlaceLat() {
        return mPlaceLat;
    }

    public void setPlaceLat(String placeLat) {
        mPlaceLat = placeLat;
        notifyPropertyChanged(BR.placeLat);
    }

    public String getPlaceLng() {
        return mPlaceLng;
    }

    public void setPlaceLng(String placeLng) {
        mPlaceLng = placeLng;
        notifyPropertyChanged(BR.placeLng);
    }

    public Long getRelationVenueID() {
        return mRelationVenueID;
    }

    public void setRelationVenueID(Long relationVenueID) {
        mRelationVenueID = relationVenueID;
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

    public String getVenueSlug() {
        return mVenueSlug;
    }

    public void setVenueSlug(String venueSlug) {
        mVenueSlug = venueSlug;
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
