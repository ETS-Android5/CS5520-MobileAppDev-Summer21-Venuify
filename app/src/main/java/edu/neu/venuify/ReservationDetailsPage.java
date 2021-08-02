package edu.neu.venuify;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Objects;

public class ReservationDetailsPage extends AppCompatActivity {
    public DatabaseReference mDatabase;
    public String venue;
    public String date;
    public String time;
    public Integer numGuests;
    public String price;
    public Button cancelButton;

    //k added
    //public ArrayList<String> arrayFromReservationCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_details_page);



        //reference the bundle information from RecyclerViewHolderReservationPage to verify with db to display correct data
        //code referenced from: https://stackoverflow.com/questions/3913592/start-an-activity-with-a-parameter
        //ArrayList<String> bundle = getIntent().getStringArrayListExtra("ReservationDetailsOfCardInRecyclerView");
        //arrayFromReservationCard = bundle;



        mDatabase = FirebaseDatabase.getInstance().getReference();
        createDatabaseListener();


        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = 0;

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
               builder.setTitle("Are you sure you would like to cancel the reservation?");

                LinearLayout layout = new LinearLayout(v.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                builder.setView(layout);

        // Set up the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //TODO: handle cancel reservation - remove the reservation from database



                                Snackbar.make(v, "Reservation Canceled", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                                //finish() should navigate to the reservations page
                                finish();
                        }


                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

    }


    private void createDatabaseListener() {
        mDatabase.child("reservations").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                        Reservation reservation = Objects.requireNonNull(snapshot.getValue(Reservation.class));


                        /*
                        //k added
                        //get details from the reservation card we click on
                        String nameFromReservationCard = arrayFromReservationCard.get(0);
                        String timeFromReservationCard = arrayFromReservationCard.get(1);
                        String dateFromReservationCard = arrayFromReservationCard.get(2);
                        //make a conditional here to make sure we display the correct reservation we click on
                        if (reservation.venue.equalsIgnoreCase(nameFromReservationCard)
                                && reservation.time.equalsIgnoreCase(timeFromReservationCard)
                                && reservation.date.equalsIgnoreCase(dateFromReservationCard)) {

                         */


                            venue = reservation.venue;
                            date = reservation.date;
                            time = reservation.time;
                            numGuests = reservation.numGuests;
                            price = reservation.price;

                            TextView venueInfo = (TextView) findViewById(R.id.venueInfo);
                            TextView dateInfo = (TextView) findViewById(R.id.dateInfo);
                            TextView timeInfo = (TextView) findViewById(R.id.timeInfo);
                            TextView numGuestsInfo = (TextView) findViewById(R.id.numGuestInfo);
                            TextView priceInfo = (TextView) findViewById(R.id.priceInfo);

                            venueInfo.setText(venue);
                            dateInfo.setText(date);
                            timeInfo.setText(time);
                            numGuestsInfo.setText(String.valueOf(numGuests));
                            priceInfo.setText(price);
                        }





                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                        Reservation reservation = Objects.requireNonNull(snapshot.getValue(Reservation.class));
                        String venue = reservation.getVenue();
                        String date = reservation.getDate();
                        String time = reservation.getTime();
                        Integer numGuests = reservation.getNumGuests();
                        String price = reservation.getPrice();

                        TextView venueInfo = (TextView) findViewById(R.id.venueInfo);
                        TextView dateInfo = (TextView) findViewById(R.id.dateInfo);
                        TextView timeInfo = (TextView) findViewById(R.id.timeInfo);
                        TextView numGuestsInfo = (TextView) findViewById(R.id.numGuestInfo);
                        TextView priceInfo = (TextView) findViewById(R.id.priceInfo);

                        venueInfo.setText(venue);
                        dateInfo.setText(date);
                        timeInfo.setText(time);
                        numGuestsInfo.setText(numGuests);
                        priceInfo.setText(price);
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
}

