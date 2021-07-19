package edu.neu.venuify.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.venuify.Models.VenueCategory;
import edu.neu.venuify.R;

public class VenueCategoryAdapter extends RecyclerView.Adapter<VenueCategoryAdapter.VenueCategoryViewHolder> {


    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<VenueCategory> venueCategories;

    public VenueCategoryAdapter(List<VenueCategory> venueCategories) {
        this.venueCategories = venueCategories;
    }

    @NonNull
    @Override
    public VenueCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_object, viewGroup, false);

        return new VenueCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueCategoryViewHolder venueCategoryViewHolder, int position) {

        VenueCategory venueCategory = venueCategories.get(position);

        venueCategoryViewHolder.VenueCategory.setText(venueCategory.getVenueCategory());

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(venueCategoryViewHolder.venueCategoryRecyclerView.getContext(),
                        LinearLayoutManager.HORIZONTAL,false);

        layoutManager.setInitialPrefetchItemCount(venueCategory.getVenueObjectList().size());


        VenueObjectAdapter venueObjectAdapter = new VenueObjectAdapter(venueCategory.getVenueObjectList());
        venueCategoryViewHolder.venueCategoryRecyclerView.setLayoutManager(layoutManager);
        venueCategoryViewHolder.venueCategoryRecyclerView.setAdapter(venueObjectAdapter);
        venueCategoryViewHolder.venueCategoryRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {

        return venueCategories.size();
    }


    static class VenueCategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView VenueCategory;
        private RecyclerView venueCategoryRecyclerView;

        VenueCategoryViewHolder(final View itemView)
        {
            super(itemView);

            VenueCategory = itemView.findViewById(R.id.parent_item_title);
            venueCategoryRecyclerView = itemView.findViewById(R.id.child_recyclerview);
        }
    }
}
