package edu.neu.venuify.reservationPage;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import edu.neu.venuify.BaseActivity;
import edu.neu.venuify.DateComparator;
import edu.neu.venuify.R;
import edu.neu.venuify.Reservation;
import edu.neu.venuify.Utils;

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

    //used to get the instance of the active user
    private FirebaseAuth mAuth;

    //adapter for the recycler view
    private RecyclerViewAdapterReservationPage recyclerViewAdapter;

    //other constants necessary for onSaveInstanceState method
    private String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";
    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (v.getId() == R.id.upcoming3button) {
            Intent j = new Intent(this, ReservationPageActivity.class);
            startActivity(j);
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


                        //before add reservations to recycler view for person, need to check the user
                        mAuth = FirebaseAuth.getInstance();
                        String currentUserUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                        //if the user id is equal the user id listed under the reservation and the reservation status is booked
                        if (currentUserUid.equals(reservation.getResUid()) && !reservation.isAvailable()) {


                            //get reservation date and send to function to evaluate if in past
                            String reservationDate = reservation.getDate();

                            //if dateIsInTheFuture is false, then add it here
                            if (!Utils.dateIsInFuture(reservationDate)) {
                                addReservationObjectToRecycler(reservation);
                            }


                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                        Reservation reservation = Objects.requireNonNull(snapshot.getValue(Reservation.class));
                        updateReservationList(reservation);
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        Reservation reservation = Objects.requireNonNull(snapshot.getValue(Reservation.class));
                        for (int i=0; i<reservationsList.size(); i++ ) {
                            if (reservationsList.get(i).getReservationId().equals(reservation.getReservationId())) {
                                reservationsList.remove(i);
                                recyclerViewAdapter.notifyItemRemoved(i);
                                break;
                            }
                        }
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

        reservationsList.add(0, reservation);
        Collections.sort(reservationsList, new DateComparator());
        Collections.reverse(reservationsList);
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

    private void updateReservationList(Reservation reservation) {

        for (int i=0; i<reservationsList.size(); i++ ) {
            if (reservation.getReservationId().equals(reservationsList.get(i).getReservationId())) {
                // remove an existing reservation that was canceled
                if (reservation.isAvailable) {
                    reservationsList.remove(i);
                    recyclerViewAdapter.notifyItemRemoved(i);
                    return;
                }

                // reservation was in the list but the user names don't match
                if (!(Objects.requireNonNull(mAuth.getCurrentUser()).getUid().equals(reservation.getResUid()))) {
                    reservationsList.remove(i);
                    recyclerViewAdapter.notifyItemRemoved(i);
                    return;
                }
                if (Utils.dateIsInFuture(reservation.getDate())) {
                    reservationsList.remove(i);
                    recyclerViewAdapter.notifyItemRemoved(i);
                    return;
                }
                else {
                    reservationsList.set(i, reservation);
                    recyclerViewAdapter.notifyItemChanged(i);
                    return;
                }
            }
        }
        // add a reservation
        if (Objects.requireNonNull(mAuth.getCurrentUser()).getUid().equals(reservation.getResUid())
                && !reservation.isAvailable() && !Utils.dateIsInFuture(reservation.getDate())) {
            addReservationObjectToRecycler(reservation);
        }
    }




}