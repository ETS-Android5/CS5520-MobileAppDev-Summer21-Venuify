package edu.neu.venuify.Adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import edu.neu.venuify.Authentication.LoginActivity;
import edu.neu.venuify.R;
import edu.neu.venuify.Models.VenueObject;
import edu.neu.venuify.VenueDetailsPage;

public class VenueObjectAdapter extends RecyclerView.Adapter<VenueObjectAdapter.VenueObjectViewHolder>  {

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


                //    TextView venueTitleOnDetailsPage = v.findViewById(R.id.venueTitleOnDetailsPg);
                //    ImageView venueImgOnDetailsPage = v.findViewById(R.id.venueImgOnDetailsPage);
                //    venueTitleOnDetailsPage.setText(venueName.getText().toString());
                  //  venueImgOnDetailsPage.setImageDrawable(imageView.getDrawable());


                    Intent i = new Intent(v.getContext(), VenueDetailsPage.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", venueName.getText().toString());
               //     Drawable drawable = imageView.getDrawable();
                 //   Bitmap bitmap = imageView.;
                 //   bundle.putParcelable("image", (Parcelable) drawable);
                    i.putExtras(bundle);
                    v.getContext().startActivity(i);


                }
            });
        }
    }


}
