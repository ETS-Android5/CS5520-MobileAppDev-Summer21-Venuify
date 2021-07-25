package edu.neu.venuify.reservationPage;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;


import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import edu.neu.venuify.BaseActivity;
import edu.neu.venuify.R;

public class ReservationPageActivity extends BaseActivity implements UpcomingListOfInvitesFragment.OnFragmentInteractionListener,
        PendingListOfInvitesFragment.OnFragmentInteractionListener, PastListOfInvitesFragment.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        configureTabLayout();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_reservation_page;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.nav_bar_reservation;
    }

    private void configureTabLayout() {
        TabLayout tabLayout = findViewById(R.id.Tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming Invites"));
        tabLayout.addTab(tabLayout.newTab().setText("Pending Invites"));
        tabLayout.addTab(tabLayout.newTab().setText("Past Reservations"));

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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