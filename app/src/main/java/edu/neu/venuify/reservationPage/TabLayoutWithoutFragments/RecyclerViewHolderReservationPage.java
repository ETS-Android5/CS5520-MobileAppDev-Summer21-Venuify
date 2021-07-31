package edu.neu.venuify.reservationPage.TabLayoutWithoutFragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import edu.neu.venuify.R;

public class RecyclerViewHolderReservationPage extends RecyclerView.ViewHolder {

    public TextView reservationName;
    public TextView reservationDate;
    public TextView reservationTime;


    public RecyclerViewHolderReservationPage(View itemView) {
        super(itemView);

        reservationName = itemView.findViewById(R.id.reservationVenueNameText);
        reservationDate = itemView.findViewById(R.id.reservationDateText);
        reservationTime = itemView.findViewById(R.id.resTimeText);


        //handles clicks on each card in the recycler view
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = getAdapterPosition();

                Snackbar.make(v, "click detected on item", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                //can set a message to popup if want when click a card
            }
        });

    }


}
