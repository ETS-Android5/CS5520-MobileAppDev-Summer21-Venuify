package edu.neu.venuify;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.venuify.Adapters.VenueCategoryAdapter;
import edu.neu.venuify.Models.VenueCategory;
import edu.neu.venuify.Models.VenueObject;

public class HomePage extends BaseActivity {
    List<VenueCategory> venueCategories = initCategories();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.homepage_activity);

//        bottomNavigationView.setSelectedItemId(R.id.nav_bar_home);


        RecyclerView venueCategoryRecyclerView = findViewById(R.id.parent_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        VenueCategoryAdapter venueCategoryAdapter = new VenueCategoryAdapter(venueCategories);


        venueCategoryRecyclerView.setLayoutManager(linearLayoutManager);
        venueCategoryRecyclerView.setAdapter(venueCategoryAdapter);

    }

    @Override
    public int getContentViewId() { return R.layout.homepage_activity; }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.nav_bar_home;
    }

    private List<VenueCategory> initCategories() {
        List<VenueCategory> venueCategoryList = new ArrayList<>();
        VenueCategory category1 = new VenueCategory("Restaurants", getRestaurants());
        venueCategoryList.add(category1);
        VenueCategory category2 = new VenueCategory("Sports", getSports());
        venueCategoryList.add(category2);
        return venueCategoryList;
    }

    private List<VenueObject> getRestaurants() {
        List<VenueObject> venueList = new ArrayList<>();
        venueList.add(new VenueObject("Restaurant 1", R.drawable.restaurant_1));
        venueList.add(new VenueObject("Restaurant 2", R.drawable.restaurant_2));
        venueList.add(new VenueObject("Restaurant 3", R.drawable.restaurant_3));
        return  venueList;
    }

    private List<VenueObject> getSports() {
        List<VenueObject> venueList = new ArrayList<>();
        venueList.add(new VenueObject("Tennis", R.drawable.tennis_ball));
        venueList.add(new VenueObject("Football", R.drawable.football_field));
        venueList.add(new VenueObject("Soccer", R.drawable.soccer_field));
        return  venueList;
    }
}
