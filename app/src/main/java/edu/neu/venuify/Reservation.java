package edu.neu.venuify;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Reservation Object implements Parcelable so when we click on a Reservation Object in the
 * recyclerview of ReservationPageActivity, we are able to send that reservation to the a
 * ReservationDetailsPage with the correct reservation information to display. The reservation
 * is sent as a Parcelable.
 */
public class Reservation implements Parcelable {
        public String venue;
        public String date;
        public String time;
        public Integer numGuests;
        public String price;
        public String user;

        public Reservation() {
            // Default constructor required for calls to DataSnapshot.getValue(Transaction.class)
        }




    public void setResUid(String resUid) {
        this.user = resUid;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNumGuests(Integer numGuests) {
        this.numGuests = numGuests;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getResUid() {
        return user;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Integer getNumGuests() {
        return numGuests;
    }

    public String getPrice() {
        return price;
    }

    public Reservation(String venue, String date, String time, Integer numGuests, String price, String resUid) {
            this.venue = venue;
            this.date = date;
            this.time = time;
            this.numGuests = numGuests;
            this.price = price;
            this.user = resUid;


        }

    //added for Parcelable
    protected Reservation(Parcel in) {
        venue = in.readString();
        date = in.readString();
        time = in.readString();
        numGuests = in.readInt();
        price = in.readString();
    }

    //added for Parcelable
    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    //added for Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    //added for Parcelable
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(venue);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeInt(numGuests);
        dest.writeString(price);

    }



}
