package edu.neu.venuify;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import edu.neu.venuify.Adapters.AvailableTimeslotAdapter;

import edu.neu.venuify.Authentication.LoginActivity;
import edu.neu.venuify.Models.VenueObject;

public class VenueDetailsPage extends AppCompatActivity {

    private TextView noAvailableMessage;
    private Spinner dateSelector;
    private ProgressBar progressBar;
    private DatabaseReference mDatabase;

    private List<Reservation> fullReservationList = new ArrayList<>();
    private List<Reservation> reservationListToDisplay = new ArrayList<>();

    private ArrayList<String> keys = new ArrayList<>();

    private RecyclerView recyclerView;

    //Keeps list of times that should be displayed based on the date selected in dropdown
    ArrayList<Reservation> availableSlotsByDayList;
    AvailableTimeslotAdapter byDayAdapter;
    LinearLayoutManager HorizontalLayout;

    ArrayAdapter<Reservation> adapter;
    VenueObject venueObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venue_details_page);
        venueObject = getIntent().getParcelableExtra("venue");
        TextView venueTitleOnDetailsPage = findViewById(R.id.venueTitleOnDetailsPg);
        venueTitleOnDetailsPage.setText(venueObject.getVenueName());
        ImageView venueImgOnDetailsPage = findViewById(R.id.venueImgOnDetailsPage);
        venueImgOnDetailsPage.setImageResource(Utils.getImageResourceId(getApplicationContext(), venueObject));

        initializeAttributes();
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSelector.setAdapter(adapter);

        handleProgressBarAndNoReservationsFound();
        loadData();
        setUpAvailableTimesHorizontalRecyclerView();

        dateSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                showAvailableTimeSlots();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //
            }
        });

        EditText numGuests = findViewById(R.id.numGuests);
        numGuests.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v.getId() == R.id.numGuests && !hasFocus) {
                    InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        EditText numGuests = findViewById(R.id.numGuests);
        numGuests.getText().clear();
    }

    private void initializeAttributes() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dateSelector = findViewById(R.id.dateSelector);
        noAvailableMessage = findViewById(R.id.noAvailableMessage);
        progressBar = findViewById(R.id.progressBar);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, reservationListToDisplay);
    }

    private void setUpAvailableTimesHorizontalRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        availableSlotsByDayList = new ArrayList<>();
        byDayAdapter = new AvailableTimeslotAdapter(availableSlotsByDayList);

        HorizontalLayout = new LinearLayoutManager(VenueDetailsPage.this, LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(byDayAdapter);

    }

    private void handleProgressBarAndNoReservationsFound() {

        //makes the progress bar disappear when the reservation either appears or no res found
        //initially set all things to GONE, then if reservations, it will later set to visible
        progressBar.setVisibility(View.GONE);
        findViewById(R.id.textView7).setVisibility(View.GONE);
        findViewById(R.id.textView8).setVisibility(View.GONE);


        //only goes through this part if there are reservations
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                if(adapter.getCount() > 0){
                    //when there are reservations available, set noAvailable to gone
                    noAvailableMessage.setVisibility(View.GONE);
                    //when there are reservations available, set "select a date" = visible
                    findViewById(R.id.textView7).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView8).setVisibility(View.VISIBLE);
                }
                else {
                    noAvailableMessage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //Shows available timeslots based on the date selected in the dropdown
    public void showAvailableTimeSlots() {

        availableSlotsByDayList.clear();
        for (Reservation r : fullReservationList) {
            if (r.getDate().equals(dateSelector.getSelectedItem().toString())) {
                availableSlotsByDayList.add(r);
            }
        }
        Collections.sort(availableSlotsByDayList, new TimeComparator());
        byDayAdapter.notifyDataSetChanged();

    }

    private void loadData() {

        mDatabase.child("reservations").addChildEventListener(
                new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {

                        Reservation reservation = Objects.requireNonNull(dataSnapshot.getValue(Reservation.class));
                        reservation.setReservationId(Objects.requireNonNull(dataSnapshot.getKey()));

                        if (isFutureAvailableReservation(reservation, venueObject)) {

                            fullReservationList.add(reservation);

                            if (!dateAlreadySeen(reservation)) {
                                reservationListToDisplay.add(reservation);
                                Collections.sort(reservationListToDisplay, new DateComparator());
                                keys.add(dataSnapshot.getKey());
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }


                    /*
                    Right now just handles the case of an available reservation for the venue
                    becoming unavailable. Removes the reservation from the selectable list
                     */
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                        Reservation changedReservation = Objects.requireNonNull(dataSnapshot.getValue(Reservation.class));

                        if (!changedReservation.isAvailable && changedReservation.venue.equals(venueObject.getVenueName())) {
                            ListIterator<Reservation> timeslotIter = availableSlotsByDayList.listIterator();
                            int count = 0;

                            while (timeslotIter.hasNext()) {
                                Reservation r = timeslotIter.next();
                                if (changedReservation.date.equals(r.date)) {
                                    count++;
                                }
                                if (changedReservation.getReservationId().equals(r.getReservationId())) {
                                    timeslotIter.remove();
                                    byDayAdapter.notifyDataSetChanged();
                                }
                            }

                            //Handles the case where the reservation that was just made needs to remove
                            // a date option from the dropdown (aka the reservation taken was the last
                            // available time on the particular date
                            if (count == 1) {
                                ListIterator<Reservation> dateDropdownIter = reservationListToDisplay.listIterator();
                                while (dateDropdownIter.hasNext()) {
                                    if (changedReservation.date.equals(dateDropdownIter.next().date)) {
                                        dateDropdownIter.remove();
                                        adapter.notifyDataSetChanged();
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext()
                                , "DBError: " + databaseError, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private boolean isFutureAvailableReservation(Reservation reservation, VenueObject venueObject) {
        return Utils.dateIsInFuture(reservation.date) && reservation.venue.equals(venueObject.getVenueName()) &&
             reservation.isAvailable;
    }

    private boolean dateAlreadySeen(Reservation r) {
        return reservationListToDisplay.contains(r);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
