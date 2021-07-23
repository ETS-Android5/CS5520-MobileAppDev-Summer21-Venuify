package edu.neu.venuify.reservationPage.RecyclerViewForFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import edu.neu.venuify.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private ArrayList<VenueObjectReservationPg> venueList;

    public RecyclerViewAdapter(ArrayList<VenueObjectReservationPg> venueList) {
        this.venueList = venueList;
    }


    @NotNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_object_reservation_page, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        VenueObjectReservationPg currentVenueObject = venueList.get(position);
        //TODO: connect to db here? (b/c no utils class)
        //holder.venueView.setImageResource(Utils.getResourceId(currentVenueObject.getImageId()));
        holder.venueDescription.setText(currentVenueObject.getVenueName());



    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
