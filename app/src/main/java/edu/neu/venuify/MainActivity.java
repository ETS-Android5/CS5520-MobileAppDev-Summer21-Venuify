package edu.neu.venuify;


import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.neu.venuify.Authentication.LoginActivity;


public class MainActivity extends BaseActivity {

    private Button logOutBtn;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        logOutBtn = findViewById(R.id.logout_btn);
        logOutBtn.setOnClickListener(view -> {
            mAuth.signOut();
            openLoginPage();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            openLoginPage();
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.homepage_activity;
    }

    @Override
    public int getNavigationMenuItemId() {
        return 0;
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void HomePageActivity(View view) {
        startActivity(new Intent(this, HomePage.class));
    }

    public void reservationDetailsPageNavigation(View view){
        Intent intentClick = new Intent(this, ReservationDetailsPage.class);
        startActivity(intentClick);
    }
}