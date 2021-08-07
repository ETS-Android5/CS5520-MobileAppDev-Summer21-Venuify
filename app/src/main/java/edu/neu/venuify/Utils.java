package edu.neu.venuify;
import android.content.ServiceConnection;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

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

    public static boolean dateIsInFuture(String date) {

        //get the current date info
        Calendar rightNow = Calendar.getInstance();
        Integer currentYear = rightNow.get(Calendar.YEAR);
        Integer currentMonth = rightNow.get(Calendar.MONTH) + 1;
        Integer currentDay = rightNow.get(Calendar.DAY_OF_MONTH);


        //split the string date that comes in (string[0]=month; string[1]=day; string[2]=year)
        String[] datePartsOfReservationDate = date.split("/");
        Integer reservationMonth = Integer.valueOf(datePartsOfReservationDate[0]);
        Integer reservationDay = Integer.valueOf(datePartsOfReservationDate[1]);
        Integer reservationYear = Integer.valueOf(datePartsOfReservationDate[2]);

        //if input year is like "21", then make the assumption that dates are in 2000's and add 2000
        if (datePartsOfReservationDate[2].length() == 2) {
            reservationYear = reservationYear + 2000;
        }

        //accounts for the weird format of 08 and 09 being too large to be an "int"
        //to see the prob try this: int i = 08;
        if (reservationMonth.toString() == "08") {
            reservationMonth = 8;
        }
        if (reservationMonth.toString() == "09") {
            reservationMonth = 9;
        }
        if (reservationDay.toString() == "08") {
            reservationDay = 8;
        }
        if (reservationDay.toString() == "09") {
            reservationDay = 9;
        }


        //if its a larger year, then its in future
        if (currentYear < reservationYear) {
            return true;
        }
        //if its this year
        if (currentYear.equals(reservationYear)) {

            //then need to check if our month is less than reservation month
            if (currentMonth < reservationMonth) {
                return true;
            }

            //if its this year, and this month, then compare day
            if (currentMonth.equals(reservationMonth)) {
                //check day
                if (currentDay <= reservationDay) {
                    return true;
                }

            }
        }

        return false;
    }

}
