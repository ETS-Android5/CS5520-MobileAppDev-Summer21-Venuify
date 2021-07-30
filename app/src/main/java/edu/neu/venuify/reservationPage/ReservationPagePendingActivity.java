package edu.neu.venuify.reservationPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.neu.venuify.BaseActivity;
import edu.neu.venuify.R;

public class ReservationPagePendingActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_page_pending);

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
            case R.id.upcoming2button:
                Intent i = new Intent(this, ReservationPageActivity.class);
                startActivity(i);
                break;

            case R.id.past2button:
                Intent j = new Intent(this, ReservationPagePastActivity.class);
                startActivity(j);
                break;
        }
    }
}