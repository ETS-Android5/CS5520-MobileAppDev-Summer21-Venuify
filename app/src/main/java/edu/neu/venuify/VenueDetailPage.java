package edu.neu.venuify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.neu.venuify.Adapters.AvailableTimeslotAdapter;
import edu.neu.venuify.Models.ReservationObject;

public class VenueDetailPage extends AppCompatActivity {

    private Spinner dateSelector;
    private DatabaseReference mDatabase;

    private List<ReservationObject> reservationList = new ArrayList<>();

    private ArrayList<String> keys = new ArrayList<>();

    private RecyclerView recyclerView;

    //Keeps list of times that should be displayed based on the date selected in dropdown
    // Todo: may need to store ReservationObject instead of string
    ArrayList<String> availableSlotsByDayList;

    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    AvailableTimeslotAdapter byDayAdapter;
    LinearLayoutManager HorizontalLayout;
    View ChildView;
    int RecylerViewItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_detail_page);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        dateSelector = findViewById(R.id.dateSelector);
        ArrayAdapter<ReservationObject> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, reservationList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSelector.setAdapter(adapter);

        mDatabase.child("reservations").addChildEventListener(
                new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {

                        ReservationObject reservation = dataSnapshot.getValue(ReservationObject.class);

                        //Todo:  need to filter out past and duplicate dates
                        if (reservation.isAvailable) {
                            reservationList.add(reservation);
                            keys.add(dataSnapshot.getKey());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                        //will use the keys array if we want to handle changes
                        //the key will identify the user object that changed
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

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        availableSlotsByDayList = new ArrayList<>();
        byDayAdapter = new AvailableTimeslotAdapter(availableSlotsByDayList);

        HorizontalLayout = new LinearLayoutManager(VenueDetailPage.this, LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(byDayAdapter);

        dateSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                showAvailableTimeSlots();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

    }

    //Shows available timeslots based on the date selected in the dropdown
    public void showAvailableTimeSlots() {

        availableSlotsByDayList.clear();
        for (ReservationObject r : reservationList) {
            if (r.date.equals(dateSelector.getSelectedItem().toString())) {
                availableSlotsByDayList.add(r.time);
            }
        }
        byDayAdapter.notifyDataSetChanged();
    }
}