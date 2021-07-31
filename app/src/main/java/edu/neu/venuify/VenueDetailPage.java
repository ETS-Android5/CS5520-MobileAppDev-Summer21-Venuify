package edu.neu.venuify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import edu.neu.venuify.Models.ReservationObject;

public class VenueDetailPage extends AppCompatActivity {

    private Spinner dateSelector;
    private DatabaseReference mDatabase;
    private final List<ReservationObject> reservationList = new ArrayList<>();
    private final ArrayList<String> keys = new ArrayList<>();

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

    }
}