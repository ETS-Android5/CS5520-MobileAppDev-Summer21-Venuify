package edu.neu.venuify.reservationPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.neu.venuify.Adapters.VenueObjectAdapter;
import edu.neu.venuify.BaseActivity;
import edu.neu.venuify.MainActivity;
import edu.neu.venuify.Models.VenueObject;
import edu.neu.venuify.R;
import edu.neu.venuify.Reservation;
import edu.neu.venuify.ReservationDetailsPage;
import edu.neu.venuify.VenueDetailsPage;

public class RecyclerViewHolderReservationPage extends RecyclerView.ViewHolder {

        public TextView reservationName;
        public TextView reservationDate;
        public TextView reservationTime;




        public RecyclerViewHolderReservationPage(@NonNull View itemView) {

            super(itemView);

            //code referenced here: https://stackoverflow.com/questions/28767413/how-to-open-a-different-activity-on-recyclerview-item-onclick
            //Context context = itemView.getContext();

            reservationName = itemView.findViewById(R.id.reservationVenueNameText);
            reservationDate = itemView.findViewById(R.id.reservationDateText);
            reservationTime = itemView.findViewById(R.id.resTimeText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();

                    //Intent i = new Intent (context, ReservationDetailsPage.class);
                    //i.putExtra("itemClickedInResList", position);
                    //context.startActivity(i);

                    RecyclerViewAdapterReservationPage venueAdapter = Objects.requireNonNull((RecyclerViewAdapterReservationPage) getBindingAdapter());
                    Reservation venueClicked = venueAdapter.getReservationList().get(position);
                    Intent intent = new Intent(v.getContext(), ReservationDetailsPage.class);
                    intent.putExtra("itemClickedInResList", venueClicked);
                    v.getContext().startActivity(intent);




                    //code referenced here: https://stackoverflow.com/questions/3913592/start-an-activity-with-a-parameter
                    //make a bundle to then access in the ReservationDetailsPage
                    //Bundle bundleForItem = new Bundle();
                    //ArrayList<String> arrayForBundle = new ArrayList<String>();
                    //arrayForBundle.add(reservationName.getText().toString());
                    //arrayForBundle.add(reservationTime.getText().toString());
                    //arrayForBundle.add(reservationDate.getText().toString());
                    //bundleForItem.putStringArrayList("ReservationDetailsOfCardInRecyclerView",arrayForBundle);



                }
            });
        }
    }


