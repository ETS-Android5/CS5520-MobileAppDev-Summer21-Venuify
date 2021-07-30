package edu.neu.venuify.reservationPage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import org.w3c.dom.Text;

import edu.neu.venuify.BaseActivity;
import java.util.ArrayList;
import java.util.Objects;

import edu.neu.venuify.R;
import edu.neu.venuify.Reservation;
import edu.neu.venuify.reservationPage.TabLayoutWithoutFragments.RecyclerViewAdapterReservationPage;


public class ReservationPageActivity extends BaseActivity implements View.OnClickListener{

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
        //setContentView(R.layout.activity_reservation_page);

        //sets bottom tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //creating the database and recycler views
        mDatabase = FirebaseDatabase.getInstance().getReference();
        createRecyclerView();
        createDatabaseListener();

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
            case R.id.pending1button:
                Intent i = new Intent(this, ReservationPagePendingActivity.class);
                startActivity(i);
                break;
            case R.id.past1button:
                Intent j = new Intent(this, ReservationPagePastActivity.class);
                startActivity(j);
                break;

        }
    }

    //creates the recycler view with the adapter and holder classes
    private void createRecyclerView() {
        RecyclerView.LayoutManager recycleLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewUpcomingReservationPage);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new RecyclerViewAdapterReservationPage(reservationsList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(recycleLayoutManager);
    }

    //gets info from database to populate list
    private void createDatabaseListener() {
        mDatabase.child("reservations").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                        Reservation reservation = Objects.requireNonNull(snapshot.getValue(Reservation.class));

                        //might need?
                        addReservationObjectToRecycler(reservation);

                        /*
                        TextView venueName = findViewById(R.id.reservationVenueNameText);
                        TextView venueDate = findViewById(R.id.reservationDateText);
                        TextView venueTime = findViewById(R.id.resTimeText);

                        venueName.setText(reservation.venue);
                        venueDate.setText(reservation.date);
                        venueTime.setText(reservation.time);

                         */







                        //may decide later to put a total count?
                        /*
                        Transaction transaction = Objects.requireNonNull(snapshot.getValue(Transaction.class));
                        if (transaction.senderUsername.equalsIgnoreCase(AuthenticatedUserSingleton.getInstance().username)) {
                            addStickerObject(transaction);

                            //add to the total number of stickers Received and set header text view
                            numberOfSent +=1;
                            TextView totalAmtOfStickersSent = findViewById(R.id.totalAmtOfStickersSentText);
                            totalAmtOfStickersSent.setText(String.valueOf(numberOfSent));

                        }

                         */
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                        Reservation reservation = Objects.requireNonNull(snapshot.getValue(Reservation.class));

                        /*
                        TextView venueName = findViewById(R.id.reservationVenueNameText);
                        TextView venueDate = findViewById(R.id.reservationDateText);
                        TextView venueTime = findViewById(R.id.resTimeText);

                        venueName.setText(reservation.venue);
                        venueDate.setText(reservation.date);
                        venueTime.setText(reservation.time);

                         */

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
    private void addReservationObjectToRecycler(Reservation reservation) {

        Reservation reservationObject = new Reservation(reservation.venue, reservation.date, reservation.time, reservation.numGuests, reservation.price);

        reservationsList.add(0, reservationObject);
        recyclerViewAdapter.notifyDataSetChanged();


    }


    //need this for when tilt screen?

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
