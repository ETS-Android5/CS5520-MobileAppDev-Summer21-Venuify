package edu.neu.venuify;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.neu.venuify.reservationPage.ReservationPageActivity;


public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {
    protected BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        navigationView = findViewById(R.id.bottom_nav_bar);
        navigationView.setOnItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.HomePageActivity) {
                startActivity(new Intent(this, HomePage.class));
            } else if (itemId == R.id.qr_activity) {
                startActivity(new Intent(this, QR_Activity.class));
            } else if (itemId == R.id.search_activity) {
                startActivity(new Intent(this, SearchActivity.class));
            } else if (itemId == R.id.reservationPageActivity) {
                startActivity(new Intent(this, ReservationPageActivity.class));
            }
            finish();
        }, 300);
        return true;
    }


    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    public abstract int getContentViewId();

    public abstract int getNavigationMenuItemId();
}
