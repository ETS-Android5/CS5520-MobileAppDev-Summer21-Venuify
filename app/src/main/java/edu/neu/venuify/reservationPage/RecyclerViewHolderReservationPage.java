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

/**
 * Holder class for the recycler view used in the ReservationPageActivity.
 */
public class RecyclerViewHolderReservationPage extends RecyclerView.ViewHolder {

        public TextView reservationName;
        public TextView reservationDate;
        public TextView reservationTime;


    //method sets onclick listener for each reservation object in your reserved reservations (upcoming/past)
    //so when click on each reservation, it takes you to the reservation details page.
    public RecyclerViewHolderReservationPage(@NonNull View itemView) {

        //code referenced here: https://stackoverflow.com/questions/28767413/how-to-open-a-different-activity-on-recyclerview-item-onclick
        //code referenced here: https://stackoverflow.com/questions/3913592/start-an-activity-with-a-parameter

            super(itemView);

            reservationName = itemView.findViewById(R.id.reservationVenueNameText);
            reservationDate = itemView.findViewById(R.id.reservationDateText);
            reservationTime = itemView.findViewById(R.id.resTimeText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();

                    RecyclerViewAdapterReservationPage venueAdapter = Objects.requireNonNull((RecyclerViewAdapterReservationPage) getBindingAdapter());
                    Reservation venueClicked = venueAdapter.getReservationList().get(position);
                    Intent intent = new Intent(v.getContext(), ReservationDetailsPage.class);
                    intent.putExtra("itemClickedInResList", venueClicked);
                    v.getContext().startActivity(intent);

                }
            });
        }
    }


