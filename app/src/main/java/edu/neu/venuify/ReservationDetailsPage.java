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
import java.util.Objects;

public class ReservationDetailsPage extends AppCompatActivity {
    public DatabaseReference mDatabase;
    public String venue;
    public String date;
    public String time;
    public Integer numGuests;
    public String price;
    public Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_details_page);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        createDatabaseListener();


        /*mDatabase = FirebaseDatabase.getInstance().getReference("reservations").child("reservation1");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Reservation reservation = snapshot1.getValue(Reservation.class);
                    String venue = reservation.getVenue();
                    String date = reservation.getDate();
                    String time = reservation.getTime();
                    Integer numGuests = reservation.getNumGuests();
                    String price = reservation.getPrice();
                    TextView venueInfoDisplay = (TextView) findViewById(R.id.venueInfo);
                    venueInfoDisplay.setText(venue);

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        };
        mDatabase.addListenerForSingleValueEvent(valueEventListener);*/



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

                        //TODO: handle cancel reservation - remove the reservation from database,
                        // and maybe navigate to the reservations page

                        // setContentView(R.layout.activity_main); ---> make is go to reservations page

                                Snackbar.make(v, "Reservation Canceled", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
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


//        TextView venueInfoDisplay = (TextView)findViewById(R.id.venueInfo);

    //getData();


    // addListener2();


//    private void getData() {
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                String value = snapshot.getChildren().toString();
//                TextView venueInfoDisplay = (TextView) findViewById(R.id.venueInfo);
//                venueInfoDisplay.setText(value);
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
//    }

    // DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("reservations");
    //createDatabaseListener();

    /*private void setReservationInfo() {
        mDatabase = FirebaseDatabase.getInstance().getReference("reservations");
        String info = mDatabase.child("reservation1").child("venue").toString();
        TextView venueInfoDisplay = (TextView) findViewById(R.id.venueInfo);
        venueInfoDisplay.setText(info);
       //.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
           @Override
           public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
               TextView venueInfoDisplay = (TextView)findViewById(R.id.venueInfo);
               venueInfoDisplay.setText(String.valueOf(task.getResult().getValue()));
           }
       });
       TextView venueInfoDisplay = (TextView)findViewById(R.id.venueInfo);
       venueInfoDisplay.setText(venuInfo);
    }*/


    /*private void addListener2() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Reservation reservation = snapshot.getValue(Reservation.class);
                //   System.out.println(reservation);

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
                numGuestsInfo.setText(numGuests);
                priceInfo.setText(price);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    private void createDatabaseListener() {
        mDatabase.child("reservations").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                        Reservation reservation = Objects.requireNonNull(snapshot.getValue(Reservation.class));
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

