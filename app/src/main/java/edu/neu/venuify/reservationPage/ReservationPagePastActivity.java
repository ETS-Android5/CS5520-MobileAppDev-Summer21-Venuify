package edu.neu.venuify.reservationPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.neu.venuify.BaseActivity;
import edu.neu.venuify.R;

public class ReservationPagePastActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_page_past);

        //sets bottom tool bar
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pending3button:
                Intent i = new Intent(this, ReservationPagePendingActivity.class);
                startActivity(i);
                break;

            case R.id.upcoming3button:
                Intent j = new Intent(this, ReservationPageActivity.class);
                startActivity(j);
                break;
        }
    }

}