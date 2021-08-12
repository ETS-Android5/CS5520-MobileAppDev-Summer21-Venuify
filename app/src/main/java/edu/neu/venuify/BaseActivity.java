package edu.neu.venuify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
        createNotificationChannel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNavigationBarState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStop() {
        startService(new Intent(this, BackgroundService.class));
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.account) {
            startActivity(new Intent(this, AccountPageActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

//        // handle case where the current item is selected again
        if (navigationView.getSelectedItemId() ==  itemId) {
            return true;
        }

        // new navigation button selected
        if (itemId == R.id.nav_bar_home) {
            startActivity(new Intent(this, HomePage.class));
            overridePendingTransition(0, 0);
            return true;

        } else if (itemId == R.id.nav_bar_qr) {
            startActivity(new Intent(this, QR_Activity.class));
            overridePendingTransition(0, 0);
            return true;

        } else if (itemId == R.id.nav_bar_search) {
            startActivity(new Intent(this, SearchActivity.class));
            overridePendingTransition(0, 0);
            return true;

        } else if (itemId == R.id.nav_bar_reservation) {
            startActivity(new Intent(this, ReservationPageActivity.class));
            overridePendingTransition(0, 0);
            return true;

        }

        return false;
    }




    void updateNavigationBarState(){
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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "VenuifyNotificationChannel";
            String description = "Notification channel for Venuify app";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notifyVenuify", channelName, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }




    public abstract int getContentViewId();

    public abstract int getNavigationMenuItemId();
}
