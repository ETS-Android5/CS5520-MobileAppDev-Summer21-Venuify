package edu.neu.venuify.Models;

import java.util.List;

public class VenueCategory {
    private String venueCategory;
    private List<VenueObject> venueObjectList;

    public VenueCategory(String venueCategory, List<VenueObject> venueObjectList) {
        this.venueCategory = venueCategory;
        this.venueObjectList = venueObjectList;
    }

    public String getVenueCategory() { return venueCategory; }

    public void setVenueCategory(String venueCategory) {
        this.venueCategory = venueCategory;
    }

    public List<VenueObject> getVenueObjectList() {
        return venueObjectList;
    }

    public void setVenueObjectList(List<VenueObject> venueObjectList) {
        this.venueObjectList = venueObjectList;
    }

}
