package edu.neu.venuify.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class VenueObject implements Parcelable {
    private String venueName;
    private String imageId;
    private String category;

    public VenueObject() {

    }

    public VenueObject(String venueName, String category, String ImageId) {

        this.venueName = venueName;
        this.imageId = ImageId;
        this.category = category;
    }

    public VenueObject(Parcel in) {
        venueName = in.readString();
        category = in.readString();
        imageId = in.readString();

    }

    public static final Creator<VenueObject> CREATOR = new Creator<VenueObject>() {
        @Override
        public VenueObject createFromParcel(Parcel in) {
            return new VenueObject(in);
        }

        @Override
        public VenueObject[] newArray(int size) {
            return new VenueObject[size];
        }
    };

    public String getVenueName() { return venueName; }

    public void setVenueName(String venueName) {

        this.venueName = venueName;
    }

    public String getImageId() { return imageId; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(venueName);
        dest.writeString(category);
        dest.writeString(imageId);
    }
}
