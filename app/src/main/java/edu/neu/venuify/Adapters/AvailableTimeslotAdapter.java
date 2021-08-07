package edu.neu.venuify.Adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.neu.venuify.R;
import edu.neu.venuify.Reservation;
import edu.neu.venuify.ReservationDetailsPage;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


/*
Adapted from https://www.geeksforgeeks.org/android-horizontal-recyclerview-with-examples/
 */

public class AvailableTimeslotAdapter extends RecyclerView.Adapter<AvailableTimeslotAdapter.MyView> {

    private List<Reservation> list;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {

        TextView textView;
        private FirebaseAuth mAuth;
        private DatabaseReference mDatabase;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view) {
            super(view);

            // initialise TextView with id
            textView = (TextView) view
                    .findViewById(R.id.availableTimeslot);
            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();


            // When a time is clicked, open a dialog asking the user if they want to confirm the
            // the reservation for the selected day and time
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    AvailableTimeslotAdapter adapter = Objects.requireNonNull((AvailableTimeslotAdapter) getBindingAdapter());
                    Reservation reservation = adapter.list.get(position);
                    String confirmTitle = "Book " + reservation.getVenue() + " on " + reservation.getDate()
                            + " at " + reservation.getTime() +"?";

                    int pos = 0;
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(confirmTitle);
                    LinearLayout layout = new LinearLayout(v.getContext());
                    layout.setOrientation(LinearLayout.VERTICAL);
                    builder.setView(layout);

                    // Set up the behavior of the yes button
                    // Clicking Yes to confirm the reservations sets isAvailable to false,
                    // add the user uid to the reservation and takes the user to
                    // the Reservation Details Page
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Map<String, Object> map = new HashMap<>();
                            map.put("/reservations/" + reservation.getReservationId() + "/isAvailable/", false);
                            map.put("/reservations/" + reservation.getReservationId() + "/user/", mAuth.getCurrentUser().getUid());
                            mDatabase.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(view.getContext(), ReservationDetailsPage.class);
                                    intent.putExtra("itemClickedInResList", reservation);
                                    view.getContext().startActivity(intent);
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle fail - can handle better if we want
                                            Toast.makeText(getApplicationContext(),"Error confirming the reservation",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });

                    //Sets up the behavior of the no button, cancels the dialog
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
    }

    // Constructor for adapter class
    // which takes a list of String type
    public AvailableTimeslotAdapter(List<Reservation> horizontalList) {
        this.list = horizontalList;
    }


    @Override
    public MyView onCreateViewHolder(ViewGroup parent,
                                     int viewType) {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.available_timeslot,
                        parent,
                        false);

        // return itemView
        return new MyView(itemView);
    }

    public void onBindViewHolder(final MyView holder,
                                 final int position) {

        holder.textView.setText(list.get(position).getTime());
    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount() {
        return list.size();
    }
}