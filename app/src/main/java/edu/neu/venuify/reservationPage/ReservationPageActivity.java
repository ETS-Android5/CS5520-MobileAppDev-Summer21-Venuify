package edu.neu.venuify.reservationPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import edu.neu.venuify.BaseActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import edu.neu.venuify.DateComparator;
import edu.neu.venuify.R;
import edu.neu.venuify.Reservation;
import edu.neu.venuify.ReservationDetailsPage;
import edu.neu.venuify.Utils;

/**
 * Class ReservationPageActivity is the page that shows all reservations as objects in a recycler
 * view. This recycler view works with the RecyclerViewAdapterReservationPage and
 * RecyclerViewHolderReservationPage.
 * Referenced A7, class textbook.
 */
public class ReservationPageActivity extends BaseActivity {

    //reference to the database
    private DatabaseReference mDatabase;

    //used to get the instance of the active user
    private FirebaseAuth mAuth;


    //list of reservations
    private ArrayList<Reservation> reservationsList = new ArrayList<>();

    //adapter for the recycler view
    private RecyclerViewAdapterReservationPage recyclerViewAdapter;

    //other constants necessary for onSaveInstanceState method
    private String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";
    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //creating the database and recycler views
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        createRecyclerView();
        createDatabaseListener();



    }

    //method for bottom navigation bar
    @Override
    public int getContentViewId() {
        return R.layout.activity_reservation_page;
    }

    //method for menu
    @Override
    public int getNavigationMenuItemId() {
        return R.id.nav_bar_reservation;
    }

    //sets the button on the "tab layout" so we can go to the past reservation recycler view
    public void onClick(View v) {
        if (v.getId() == R.id.past1button) {
            Intent j = new Intent(this, ReservationPagePastActivity.class);
            startActivity(j);
        }
    }

    //creates the recycler view with the adapter and holder classes
    private void createRecyclerView() {
        RecyclerView.LayoutManager recycleLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewUpcomingReservationPage);
        recyclerView.setHasFixedSize(true);
        //Collections.sort(reservationsList, new DateComparator()); //maybe doesn't work?
        recyclerViewAdapter = new RecyclerViewAdapterReservationPage(reservationsList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(recycleLayoutManager);
    }

    //gets info from database to populate the recycler view of reservations you have in the upcoming list
    private void createDatabaseListener() {
        mDatabase.child("reservations").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                        Reservation reservation = Objects.requireNonNull(snapshot.getValue(Reservation.class));

                        //before add reservations to recycler view for person, need to check the user
                        String currentUserUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                        //if the user id is equal the user id listed under the reservation and the reservation status is booked
                        if (currentUserUid.equals(reservation.getResUid()) && !reservation.isAvailable()) {


                            //get reservation date and send to function to evaluate if in past
                            String reservationDate = reservation.getDate();


                            //if dateIsInTheFuture is true, then add it here
                            if (Utils.dateIsInFuture(reservationDate)) {
                                addReservationObjectToRecycler(reservation);
                            }

                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                        Reservation reservation = Objects.requireNonNull(snapshot.getValue(Reservation.class));

                        int pos = reservationsList.indexOf(reservation);
                        reservationsList.set(pos, reservation);
                        recyclerViewAdapter.notifyItemChanged(pos);

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        Reservation reservation = Objects.requireNonNull(snapshot.getValue(Reservation.class));

                        int pos = reservationsList.indexOf(reservation);
                        reservationsList.remove(pos);
                        recyclerViewAdapter.notifyItemChanged(pos);

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                }
        );
    }
    //adds a reservation object to the recycler view of all upcoming reservations
    private void addReservationObjectToRecycler(Reservation reservation) {
        reservationsList.add(0, reservation);
        Collections.sort(reservationsList, new DateComparator());
        recyclerViewAdapter.notifyDataSetChanged();
    }


    //need this for when tilt screen
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        int size = reservationsList == null ? 0 : reservationsList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        for (int i = 0; i < size; i++) {
            outState.putString(KEY_OF_INSTANCE + i + "0", reservationsList.get(i).getVenue());
        }
        super.onSaveInstanceState(outState);
    }


}
