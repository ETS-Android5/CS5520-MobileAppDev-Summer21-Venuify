package edu.neu.venuify;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.neu.venuify.Adapters.VenueCategoryAdapter;
import edu.neu.venuify.Models.VenueCategory;
import edu.neu.venuify.Models.VenueObject;

public class HomePage extends BaseActivity {
    List<VenueCategory> venueCategories;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    VenueCategoryAdapter venueCategoryAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView venueCategoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildCategories();
        getVenuesFromDatabase();
        venueCategoryRecyclerView = findViewById(R.id.parent_recyclerview);

    }

    @Override
    public int getContentViewId() { return R.layout.homepage_activity; }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.nav_bar_home;
    }


    private void getVenuesFromDatabase() {
        databaseReference.child("Venues").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    VenueObject venueObject = Objects.requireNonNull(dataSnapshot.getValue(VenueObject.class));
                    venueCategories.forEach(category -> {
                        if (category.getVenueCategory().equals(venueObject.getCategory())) {
                            category.getVenueObjectList().add(venueObject);


                        }
                    });
                }
                //TODO: Need to improve loading
                venueCategoryAdapter = new VenueCategoryAdapter(venueCategories);
                linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                venueCategoryRecyclerView.setLayoutManager(linearLayoutManager);
                venueCategoryRecyclerView.setAdapter(venueCategoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void buildCategories() {
        VenueCategory restaurants = new VenueCategory(Utils.Category.RESTAURANTS.toString(), new ArrayList<VenueObject>());
        VenueCategory sports = new VenueCategory(Utils.Category.SPORTS.toString(), new ArrayList<VenueObject>());
        VenueCategory music = new VenueCategory(Utils.Category.MUSIC.toString(), new ArrayList<VenueObject>());
        VenueCategory workspace = new VenueCategory(Utils.Category.WORKSPACE.toString(), new ArrayList<VenueObject>());
        venueCategories = new ArrayList<>();
        venueCategories.addAll(List.of(restaurants, sports, music, workspace));
    }

}
