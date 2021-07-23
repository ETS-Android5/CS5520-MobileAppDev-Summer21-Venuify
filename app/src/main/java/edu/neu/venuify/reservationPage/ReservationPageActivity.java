package edu.neu.venuify.reservationPage;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

//import edu.neu.venuify.reservationPage.databinding.ActivityReservationPage1Binding;

import java.util.ArrayList;

import edu.neu.venuify.R;
import edu.neu.venuify.reservationPage.RecyclerViewForFragments.RecyclerViewAdapter;
import edu.neu.venuify.reservationPage.RecyclerViewForFragments.VenueObjectReservationPg;
import edu.neu.venuify.reservationPage.TabLayoutAndFragments.PastListOfInvitesFragment;
import edu.neu.venuify.reservationPage.TabLayoutAndFragments.PendingListOfInvitesFragment;
import edu.neu.venuify.reservationPage.TabLayoutAndFragments.TabPagerAdapter;
import edu.neu.venuify.reservationPage.TabLayoutAndFragments.UpcomingListOfInvitesFragment;

/**
 * Adapted from Chapter 47, Android Studio 4.1 Development Essentials
 */
public class ReservationPageActivity extends AppCompatActivity implements UpcomingListOfInvitesFragment.OnFragmentInteractionListener,
        PendingListOfInvitesFragment.OnFragmentInteractionListener, PastListOfInvitesFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        configureTabLayout();
    }

    private void configureTabLayout() {

        //sets the tabs with corresponding names
        TabLayout tabLayout = findViewById(R.id.Tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming Invites"));
        tabLayout.addTab(tabLayout.newTab().setText("Pending Invites"));
        tabLayout.addTab(tabLayout.newTab().setText("Past Reservations"));

        //get reference for pager instance, and make an instance of the TabPagerAdapter
        //adapter is set as the adapter for the viewPager
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        //tabLayout is added to the page change listener
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //recyclerview doesn't work yet
                //createRecyclerView();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public void createRecyclerView() {
        //not sure about context yet
        RecyclerView.LayoutManager recycleLayoutManager = new LinearLayoutManager(this);

        //setting just the first fragment for now
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewUpcomingVenues);
        recyclerView.setHasFixedSize(true);

        //hard code a list of things for now
        ArrayList<VenueObjectReservationPg> venueList = new ArrayList<>();
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(venueList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(recycleLayoutManager);




    }








    /*
    private AppBarConfiguration appBarConfiguration;
    private ActivityReservationPage1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReservationPage1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_reservation_page1);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_reservation_page1);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

     */
}