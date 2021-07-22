package edu.neu.venuify.reservationPage.RecyclerViewForFragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.neu.venuify.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView venueView;
    public TextView venueDescription;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        venueView = itemView.findViewById(R.id.venueImage);
        venueDescription = itemView.findViewById(R.id.venueReservationInfo);

    }



}
