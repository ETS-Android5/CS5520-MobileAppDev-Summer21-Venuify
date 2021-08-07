package edu.neu.venuify;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.neu.venuify.Models.VenueCategory;
import edu.neu.venuify.Models.VenueObject;

public class Utils {
    static List<VenueCategory> venueCategories;

    public static void createVenueDatabase() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<VenueCategory> venueCategoryList = new ArrayList<>();
                VenueCategory category1 = new VenueCategory("Restaurants", getRestaurants());
                VenueCategory category2 = new VenueCategory("Sports", getSports());
                VenueCategory category3 = new VenueCategory("Music", getMusic());
                VenueCategory category4 = new VenueCategory("Workspace", getWorkSpace());
                venueCategoryList.add(category1);
                venueCategoryList.add(category2);
                venueCategoryList.add(category3);
                venueCategoryList.add(category4);
                venueCategories = venueCategoryList;
                writeToDatabase();
            }
        }).start();

    }


    private static void writeToDatabase() {

        for ( int i = 0; i < venueCategories.size(); i++) {
            VenueCategory venueCategory = venueCategories.get(i);
            DatabaseReference venueRef = FirebaseDatabase.getInstance().getReference("Venues");
            List<VenueObject> venuesList = venueCategory.getVenueObjectList();
            for ( int j = 0; j < venuesList.size(); j++) {
                VenueObject venue = venuesList.get(j);
                String key = Objects.requireNonNull(venueRef.push().getKey());
                venueRef.child(key).setValue(venue);
            }
        }

    }


    private static List<VenueObject> getRestaurants() {
        List<VenueObject> venueList = new ArrayList<>();
        venueList.add(new VenueObject("Restaurant 1", Category.RESTAURANTS.toString(),  R.drawable.restaurant_1));
        venueList.add(new VenueObject("Restaurant 2", Category.RESTAURANTS.toString(), R.drawable.restaurant_2));
        venueList.add(new VenueObject("Restaurant 3", Category.RESTAURANTS.toString(), R.drawable.restaurant_3));
        venueList.add(new VenueObject("Restaurant 4", Category.RESTAURANTS.toString(), R.drawable.restaurant_4));
        venueList.add(new VenueObject("Restaurant 5", Category.RESTAURANTS.toString(), R.drawable.restaurant_5));
        venueList.add(new VenueObject("Restaurant 6", Category.RESTAURANTS.toString(), R.drawable.restaurant_6));
        return  venueList;
    }

    private static List<VenueObject> getSports() {
        List<VenueObject> venueList = new ArrayList<>();
        venueList.add(new VenueObject("Tennis", Category.SPORTS.toString(), R.drawable.tennis_ball));
        venueList.add(new VenueObject("Football Field", Category.SPORTS.toString(), R.drawable.football_field));
        venueList.add(new VenueObject("Soccer Field", Category.SPORTS.toString(), R.drawable.soccer_field));
        venueList.add(new VenueObject("Basketball Court", Category.SPORTS.toString(), R.drawable.basketball_court));
        venueList.add(new VenueObject("Archery", Category.SPORTS.toString(), R.drawable.archery));
        venueList.add(new VenueObject("Rock Climbing", Category.SPORTS.toString(), R.drawable.rock_climbing));
        return  venueList;
    }

    private static List<VenueObject> getMusic() {
        List<VenueObject> venueList = new ArrayList<>();
        venueList.add(new VenueObject("Concert Hall", Category.MUSIC.toString(), R.drawable.concert));
        venueList.add(new VenueObject("Band", Category.MUSIC.toString(), R.drawable.band_stage));
        venueList.add(new VenueObject("DJ", Category.MUSIC.toString(), R.drawable.turn_table));
        venueList.add(new VenueObject("Recording Studio", Category.MUSIC.toString(), R.drawable.microphone));

        return  venueList;
    }

    private static List<VenueObject> getWorkSpace() {
        List<VenueObject> venueList = new ArrayList<>();
        venueList.add(new VenueObject("Wood Shop", Category.WORKSPACE.toString(), R.drawable.wood_working));
        venueList.add(new VenueObject("Yoga", Category.WORKSPACE.toString(), R.drawable.yoga));
        venueList.add(new VenueObject("Office Space 1", Category.WORKSPACE.toString(), R.drawable.office_space_1));
        venueList.add(new VenueObject("Office Space 2", Category.WORKSPACE.toString(), R.drawable.office_space_2));
        venueList.add(new VenueObject("Art Gallery", Category.WORKSPACE.toString(), R.drawable.art_gallery));
        venueList.add(new VenueObject("Paint Shop", Category.WORKSPACE.toString(), R.drawable.paint_supplies));
        return  venueList;
    }

    public enum Category {
        RESTAURANTS("Restaurants"),
        SPORTS("Sports"),
        MUSIC("Music"),
        WORKSPACE("Workspace");

        private final String text;


        Category(final String text) {
            this.text = text;
        }

        @NotNull
        @Override
        public String toString() {
            return text;
        }
    }

}
