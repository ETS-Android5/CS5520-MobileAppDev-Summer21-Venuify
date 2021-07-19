package edu.neu.venuify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.venuify.R;
import edu.neu.venuify.Models.VenueObject;

public class VenueObjectAdapter extends RecyclerView.Adapter<VenueObjectAdapter.VenueObjectViewHolder> {
    private List<VenueObject> venueObjectList;
    Context cxt;

    public VenueObjectAdapter(List<VenueObject> venueObjectList, Context context) {
        this.venueObjectList = venueObjectList;
        this.cxt = context;
    }

    public class VenueObjectViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView venueName;

        public VenueObjectViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_child_item);
            venueName = itemView.findViewById(R.id.child_item_title);
        }
    }


    @NonNull
    @Override
    public VenueObjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(cxt).inflate(R.layout.venue_object, viewGroup, false);
        return new VenueObjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueObjectViewHolder venueHolder, int position) {
        VenueObject venueObject = venueObjectList.get(position);
        venueHolder.imageView.setImageResource(venueObject.getImageId());
        venueHolder.venueName.setText(venueObject.getVenueName());

    }

    @Override
    public int getItemCount() {
        return venueObjectList.size();
    }


}
