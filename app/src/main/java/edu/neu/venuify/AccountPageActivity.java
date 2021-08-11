package edu.neu.venuify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import edu.neu.venuify.Authentication.LoginActivity;
import edu.neu.venuify.Entities.User;

public class AccountPageActivity extends AppCompatActivity {

    private Button logOutBtn;
    private ImageView goBack;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    final AtomicInteger count = new AtomicInteger();
    private TextView numReservations;
    private ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings_page);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        numReservations = findViewById(R.id.numReservations);

        mDatabase.child("users").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                        User user = Objects.requireNonNull(snapshot.getValue(User.class));


                        //get current user Id from Firebase
                        String currentUserUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                        //if the user id from Firebase equals the user id of the User from User class
                        if (currentUserUid.equals(user.getUid())) {

                            //display first and last name of user on account page
                            String userFirstName = user.getFirstName();
                            String userLastName = user.getLastName();

                            TextView firstN = findViewById(R.id.firstName);
                            TextView lastN = findViewById(R.id.lastName);

                            firstN.setText(userFirstName);
                            lastN.setText(userLastName);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                }
        );



        // LOG OUT button
        logOutBtn = findViewById(R.id.logout_btn2);
        logOutBtn.setOnClickListener(view -> {
            mAuth.signOut();
            openLoginPage();
        });

        // GoBack Button
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(view -> {
            finish();
        });


        getNumReservations();

    }

    public void openLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void getNumReservations() {

        mDatabase.child("reservations").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                // New child added, increment count
                Reservation reservation = dataSnapshot.getValue(Reservation.class);
                if (reservation.user.equals(mAuth.getCurrentUser().getUid())) {
                    int newCount = count.incrementAndGet();
                    //numReservations.setText(Integer.toString(newCount));
                }
                numReservations.setText(String.valueOf(count));

                pBar = findViewById(R.id.progress_bar);
                pBar.setProgress(Integer.parseInt(String.valueOf(count)));

                TextView tv = findViewById(R.id.tv);
                tv.setText(count + "/" + pBar.getMax());

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
            // ...
        });
    }



}
