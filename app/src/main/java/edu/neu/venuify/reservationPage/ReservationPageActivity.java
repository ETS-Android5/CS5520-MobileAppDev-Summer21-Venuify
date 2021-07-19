package edu.neu.venuify.reservationPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import edu.neu.venuify.R;

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
}