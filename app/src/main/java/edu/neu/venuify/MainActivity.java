package edu.neu.venuify;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

import edu.neu.venuify.Authentication.LoginActivity;
import edu.neu.venuify.reservationPage.ReservationPageActivity;


public class MainActivity extends AppCompatActivity {

    private Button logOutBtn;
    FirebaseAuth mAuth;
    private TextView numReservations;
    private DatabaseReference mDatabase;
    final AtomicInteger count = new AtomicInteger();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
    public void reservationPageActivity(View view){
        Intent intentForRes = new Intent(this, ReservationPageActivity.class);
        startActivity(intentForRes);
    }
}