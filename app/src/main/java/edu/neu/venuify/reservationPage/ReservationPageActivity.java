package edu.neu.venuify.reservationPage;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import edu.neu.venuify.BaseActivity;
import java.util.ArrayList;

import edu.neu.venuify.R;


public class ReservationPageActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_reservation_page);

        //sets bottom tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

    //method for bottom nav bar
    @Override
    public int getContentViewId() {
        return R.layout.activity_reservation_page;
    }

    //method for bottom nav bar
    @Override
    public int getNavigationMenuItemId() {
        return R.id.nav_bar_reservation;
    }


}
