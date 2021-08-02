package edu.neu.venuify.Models;

public class ReservationObject {

    public String uid;
    public boolean isAvailable;
    public String date;
    public int numGuests;
    public String price;
    public String time;
    public String venue;

    public ReservationObject() {
    }

    @Override
    public String toString() {
        return date;
    }
}
