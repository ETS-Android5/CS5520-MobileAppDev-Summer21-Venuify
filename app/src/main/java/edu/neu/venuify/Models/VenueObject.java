package edu.neu.venuify.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class VenueObject implements Parcelable {
    private String venueName;
    private int imageId;

    public VenueObject() {

    }

    public VenueObject(String venueName, int ImageId) {

        this.venueName = venueName;
        this.imageId = ImageId;
    }

    public VenueObject(Parcel in) {
        venueName = in.readString();
        imageId = in.readInt();
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

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {

        this.venueName = venueName;
    }

    public int getImageId() {
        return imageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(venueName);
        dest.writeInt(imageId);
    }
}
