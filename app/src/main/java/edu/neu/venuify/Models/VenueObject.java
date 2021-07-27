package edu.neu.venuify.Models;

public class VenueObject {
    private String venueName;
    private int imageId;

    public VenueObject() {

    }

    public VenueObject(String venueName, int ImageId) {

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
}
