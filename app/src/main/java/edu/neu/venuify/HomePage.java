package edu.neu.venuify;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.neu.venuify.Adapters.VenueCategoryAdapter;
import edu.neu.venuify.Authentication.LoginActivity;
import edu.neu.venuify.Models.VenueCategory;
import edu.neu.venuify.Models.VenueObject;

public class HomePage extends BaseActivity {
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    List<VenueCategory> venueCategories;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    VenueCategoryAdapter venueCategoryAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView venueCategoryRecyclerView;
    ProgressBar loadingBar;
    boolean isInitialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        venueCategoryRecyclerView = findViewById(R.id.parent_recyclerview);
        loadingBar = findViewById(R.id.loadingBar);
        if (mAuth.getCurrentUser() != null) {
            buildCategories();
            getVenuesFromDatabase();
            databaseTimeout();
            stopService(new Intent(this, BackgroundService.class).putExtra("stopTimer", true));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            openLoginPage();
        }
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
                            Utils.venueMap.put(venueObject.getVenueName(), dataSnapshot.getKey());
                        }
                    });
                }
                if (!isInitialized) {
                    venueCategoryAdapter = new VenueCategoryAdapter(venueCategories);
                    venueCategoryAdapter.setHasStableIds(true);
                    linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    venueCategoryRecyclerView.setLayoutManager(linearLayoutManager);
                    venueCategoryRecyclerView.setAdapter(venueCategoryAdapter);
                    loadingBar.setVisibility(View.GONE);
                    venueCategoryRecyclerView.setVisibility(View.VISIBLE);
                    isInitialized = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Could not load venues from database", Toast.LENGTH_LONG).show();
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




    private void databaseTimeout() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (venueCategoryAdapter == null) {
                    Toast.makeText(getApplicationContext(), "Could not load venues from database.", Toast.LENGTH_LONG).show();
                    loadingBar.setVisibility(View.INVISIBLE);
                }
            }
        }, 10000);
    }

//    private void handleIntent(Intent intent) {
//        if (intent.hasExtra("stopTimer")) {
//
//        }
//    }

    public void openLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
