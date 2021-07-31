package edu.neu.venuify.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import edu.neu.venuify.R;
import edu.neu.venuify.Models.VenueObject;

public class VenueObjectAdapter extends RecyclerView.Adapter<VenueObjectAdapter.VenueObjectViewHolder> {

    private List<VenueObject> venueObjectList;

    public VenueObjectAdapter(List<VenueObject> venueObjectList) {
        this.venueObjectList = venueObjectList;
    }

    @NonNull
    @Override
    public VenueObjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.venue_object, viewGroup, false);
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

    public void setVenueObjectList(List<VenueObject> venueObjectList) {
        this.venueObjectList = venueObjectList;
    }

    public void clearVenueObjectList() {
        this.venueObjectList.clear();
        notifyDataSetChanged();
    }

    static class VenueObjectViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView venueName;

        public VenueObjectViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_child_item);
            venueName = itemView.findViewById(R.id.child_item_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    Snackbar.make(v, "Click detected on " + venueName.getText().toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }


}
