
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

    public String reservationId;
    public String venue;
    public String date;
    public String time;
    public Integer numGuests;
    public String price;
    public String user;
    public boolean isAvailable;

    public Reservation() {
        // Default constructor required for calls to DataSnapshot.getValue(Reservation.class)
    }


    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
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

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    //added for Parcelable
    protected Reservation(Parcel in) {
        reservationId = in.readString();
        venue = in.readString();
        date = in.readString();
        time = in.readString();
        numGuests = in.readInt();
        price = in.readString();
        user = in.readString();
        isAvailable = in.readBoolean();

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
        dest.writeString(reservationId);
        dest.writeString(venue);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeInt(numGuests);
        dest.writeString(price);
        dest.writeString(user);
        dest.writeBoolean(isAvailable);

    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Reservation)) {
            return false;
        }

        Reservation r = (Reservation) o;

        return date.equals(r.date) && venue.equals((r.venue));

    }
}