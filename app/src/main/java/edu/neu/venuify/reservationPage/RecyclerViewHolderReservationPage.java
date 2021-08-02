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

import edu.neu.venuify.BaseActivity;
import edu.neu.venuify.MainActivity;
import edu.neu.venuify.R;
import edu.neu.venuify.ReservationDetailsPage;

public class RecyclerViewHolderReservationPage extends RecyclerView.ViewHolder {

        public TextView reservationName;
        public TextView reservationDate;
        public TextView reservationTime;





        public RecyclerViewHolderReservationPage(@NonNull View itemView) {

            super(itemView);

            //code referenced here: https://stackoverflow.com/questions/28767413/how-to-open-a-different-activity-on-recyclerview-item-onclick
            Context context = itemView.getContext();

            reservationName = itemView.findViewById(R.id.reservationVenueNameText);
            reservationDate = itemView.findViewById(R.id.reservationDateText);
            reservationTime = itemView.findViewById(R.id.resTimeText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    Intent i = new Intent (context, ReservationDetailsPage.class);

                    //code referenced here: https://stackoverflow.com/questions/3913592/start-an-activity-with-a-parameter
                    //make a bundle to then access in the ReservationDetailsPage
                    Bundle bundleForItem = new Bundle();
                    ArrayList<String> arrayForBundle = new ArrayList<String>();
                    arrayForBundle.add(reservationName.getText().toString());
                    arrayForBundle.add(reservationTime.getText().toString());
                    arrayForBundle.add(reservationDate.getText().toString());

                    bundleForItem.putStringArrayList("ReservationDetailsOfCardInRecyclerView",arrayForBundle);

                    context.startActivity(i);

                }
            });
        }
    }


