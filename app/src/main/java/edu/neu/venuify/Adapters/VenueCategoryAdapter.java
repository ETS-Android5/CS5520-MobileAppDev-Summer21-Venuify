package edu.neu.venuify.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.venuify.Models.VenueCategory;
import edu.neu.venuify.R;

public class VenueCategoryAdapter extends RecyclerView.Adapter<VenueCategoryAdapter.VenueCategoryViewHolder> {


    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<VenueCategory> venueCategories;

    public VenueCategoryAdapter(List<VenueCategory> venueCategories) {
        this.venueCategories = venueCategories;
        this.setHasStableIds(true);
    }

    @NonNull
    @Override
    public VenueCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_object, viewGroup, false);
        VenueCategoryViewHolder venueCategoryViewHolder = new VenueCategoryViewHolder(view);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(venueCategoryViewHolder.childRecyclerView.getContext(),
                        LinearLayoutManager.HORIZONTAL,false);
        venueCategoryViewHolder.childRecyclerView.setLayoutManager(layoutManager);
        venueCategoryViewHolder.childRecyclerView.setRecycledViewPool(viewPool);
        venueCategoryViewHolder.childRecyclerView.setHasFixedSize(true);
        venueCategoryViewHolder.childRecyclerView.setItemViewCacheSize(10);

        return venueCategoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VenueCategoryViewHolder venueCategoryViewHolder, int position) {

        VenueCategory venueCategory = venueCategories.get(position);

        venueCategoryViewHolder.VenueCategory.setText(venueCategory.getVenueCategory());
        VenueObjectAdapter venueObjectAdapter = new VenueObjectAdapter(venueCategory.getVenueObjectList());
        venueCategoryViewHolder.childRecyclerView.setAdapter(venueObjectAdapter);
    }

    @Override
    public int getItemCount() {
        if (venueCategories == null) {
            return 0;
        }
        return venueCategories.size();
    }


    static class VenueCategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView VenueCategory;
        private RecyclerView childRecyclerView;

        VenueCategoryViewHolder(final View itemView)
        {
            super(itemView);

            VenueCategory = itemView.findViewById(R.id.parent_item_title);
            childRecyclerView = itemView.findViewById(R.id.child_recyclerview);
        }
    }
}
