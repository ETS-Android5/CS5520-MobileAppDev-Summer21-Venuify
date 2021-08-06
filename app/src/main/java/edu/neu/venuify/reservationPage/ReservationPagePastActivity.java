package edu.neu.venuify.reservationPage;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import edu.neu.venuify.BaseActivity;
import edu.neu.venuify.R;
import edu.neu.venuify.Reservation;

/**
 * Class ReservationPagePastActivity is the page that shows all reservations as objects in a recycler
 * view. This recycler view works with the RecyclerViewAdapterReservationPage and
 * RecyclerViewHolderReservationPage.
 * Referenced A7, class textbook.
 */
public class ReservationPagePastActivity extends BaseActivity {

    //database reference
    private DatabaseReference mDatabase;

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
        //setContentView(R.layout.activity_reservation_page_past);

        //TODO: need to fix here
        //sets bottom tool bar
        Toolbar toolbar = findViewById(R.id.toolbarPast);
        setSupportActionBar(toolbar);

        //creating the database and recycler views
        mDatabase = FirebaseDatabase.getInstance().getReference();
        createRecyclerView();
        createDatabaseListener();
    }

    //method for bottom navigation bar
    @Override
    public int getContentViewId() {
        return R.layout.activity_reservation_page_past;
    }

    //method for menu
    @Override
    public int getNavigationMenuItemId() {
        return R.id.nav_bar_reservation;
    }


    //sets onclick for tab layout
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.upcoming3button:
                Intent j = new Intent(this, ReservationPageActivity.class);
                startActivity(j);
                break;
        }
    }

    //creates the recycler view with the adapter and holder classes
    private void createRecyclerView() {
        RecyclerView.LayoutManager recycleLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewPastReservationPage);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new RecyclerViewAdapterReservationPage(reservationsList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(recycleLayoutManager);
    }

    //gets info from database to populate recycler view list of past reservations
    private void createDatabaseListener() {
        mDatabase.child("reservations").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                        Reservation reservation = Objects.requireNonNull(snapshot.getValue(Reservation.class));


                        //get the date according to the current info
                        Integer currentYear = Integer.valueOf(new Date().getYear() + 1900);
                        Integer currentMonth = Integer.valueOf(new Date().getMonth());
                        Integer currentDay = Integer.valueOf(new Date().getDay());

                        //get the reservation date
                        String reservationDate = reservation.getDate();
                        String[] datePartsOfReservationDate = reservationDate.split("/");
                        Integer reservationMonth = Integer.valueOf(datePartsOfReservationDate[0]);

                        //accounts for the weird format of 08 and 09 being too large to be an "int"
                        if (reservationMonth.toString() == "08") {
                            reservationMonth = 8;
                        }
                        if (reservationMonth.toString() == "09") {
                            reservationMonth = 9;
                        }

                        Integer reservationDay = Integer.valueOf(datePartsOfReservationDate[1]);

                        //accounts for the weird format of 08 and 09 being too large to be an "int"
                        //to see the prob try this: int i = 08;
                        if (reservationDay.toString() == "08") {
                            reservationDay = 8;
                        }
                        if (reservationDay.toString() == "09") {
                            reservationDay = 9;
                        }


                        Integer reservationYear = Integer.valueOf(datePartsOfReservationDate[2]);

                        //conditional that if the date is in the past
                        if (currentYear> reservationYear) {
                            addReservationObjectToRecycler(reservation);
                            return;
                        }
                        //if its this year
                        if (currentYear.equals(reservationYear)) {
                            //then need to check if our month is less than reservation month
                            if (currentMonth > reservationMonth) {
                                addReservationObjectToRecycler(reservation);
                                return;
                            }

                            //if its this year, and this month, then compare day
                            if (currentMonth.equals(reservationMonth)) {
                                //check day
                                if (currentDay > reservationDay) {
                                    addReservationObjectToRecycler(reservation);
                                    return;
                                }

                            }
                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                        Reservation reservation = Objects.requireNonNull(snapshot.getValue(Reservation.class));
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
    }
    //add reservation object to the recycler view of past reservations
    private void addReservationObjectToRecycler(Reservation reservation) {

        Reservation reservationObject = new Reservation(reservation.venue, reservation.date, reservation.time, reservation.numGuests, reservation.price);

        reservationsList.add(0, reservationObject);
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