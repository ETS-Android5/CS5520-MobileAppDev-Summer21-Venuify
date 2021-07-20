package edu.neu.venuify;

public class Reservation {
        public String venue;
        public String date;
        public String time;
        public Integer numGuests;
        public String price;

        public Reservation() {
            // Default constructor required for calls to DataSnapshot.getValue(Transaction.class)
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

    public Reservation(String venue, String date, String time, Integer numGuests, String price) {
            this.venue = venue;
            this.date = date;
            this.time = time;
            this.numGuests = numGuests;
            this.price = price;

        }




}
