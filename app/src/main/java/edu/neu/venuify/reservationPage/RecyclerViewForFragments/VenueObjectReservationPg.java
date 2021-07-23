package edu.neu.venuify.reservationPage.RecyclerViewForFragments;


//need a separate venue object for the reservation pg b/c of layout in recycler view?
public class VenueObjectReservationPg {
    private String venueName;
    private int imageId;

    public VenueObjectReservationPg(String venueName, int ImageId) {
        this.venueName = venueName;
        this.imageId = ImageId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public int getImageId() {
        return imageId;
    }


    //will need more getters here for displaying in recyclerview reservation page


}
