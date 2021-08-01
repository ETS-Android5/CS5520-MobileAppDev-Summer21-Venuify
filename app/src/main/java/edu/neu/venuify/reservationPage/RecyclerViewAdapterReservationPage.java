package edu.neu.venuify.reservationPage;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import edu.neu.venuify.R;
import edu.neu.venuify.Reservation;
import edu.neu.venuify.ReservationDetailsPage;

public class RecyclerViewAdapterReservationPage extends RecyclerView.Adapter<RecyclerViewAdapterReservationPage.RecyclerViewHolderReservationPage>{
    private List<Reservation> reservationsList;

    public RecyclerViewAdapterReservationPage(List<Reservation> reservations) {
        this.reservationsList = reservations;
    }

    //creates the venue object layout for the recycler view
    @NonNull
    @Override
    public RecyclerViewHolderReservationPage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_object_reservation_page, parent, false);
        return new RecyclerViewHolderReservationPage(view);
    }

    //sets things in the recycler view card based on the reservation objects
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderReservationPage holder, int position) {
        Reservation currentReservationObject = reservationsList.get(position);
        //holder.stickerView.setImageResource(Utils.getResourceId(String.valueOf(currentStickerObject.getImageId())));
        holder.reservationName.setText(currentReservationObject.getVenue());
        holder.reservationTime.setText(currentReservationObject.getTime());
        holder.reservationDate.setText(currentReservationObject.getDate());
    }

    @Override
    public int getItemCount() {
        return reservationsList.size();
    }



    //added in k
    static class RecyclerViewHolderReservationPage extends RecyclerView.ViewHolder {
        public TextView reservationName;
        public TextView reservationDate;
        public TextView reservationTime;

        public RecyclerViewHolderReservationPage(@NonNull View itemView) {
            super(itemView);

            reservationName = itemView.findViewById(R.id.reservationVenueNameText);
            reservationDate = itemView.findViewById(R.id.reservationDateText);
            reservationTime = itemView.findViewById(R.id.resTimeText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    //Snackbar.make(v, "Click detected on " + reservationName.getText().toString(), Snackbar.LENGTH_LONG)
                     //       .setAction("Action", null).show();
                    //reservationName.setText("!!!!!");

                }
            });
        }
    }








}
