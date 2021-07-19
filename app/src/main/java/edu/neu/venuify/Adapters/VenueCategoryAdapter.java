package edu.neu.venuify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.venuify.R;
import edu.neu.venuify.Models.VenueCategory;

public class VenueCategoryAdapter extends RecyclerView.Adapter<VenueCategoryAdapter.VenueCategoryViewHolder> {

    private RecyclerView.RecycledViewPool viewPool;
    private List<VenueCategory> venueCategories;
    private VenueObjectAdapter venueObjectAdapter;
    public Context cxt;

    public VenueCategoryAdapter(List<VenueCategory> venueCategories, Context context) {
        this.venueCategories = venueCategories;
        this.cxt = context;
        viewPool = new RecyclerView.RecycledViewPool();
    }

    public class VenueCategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryTitle;
        public RecyclerView venueCategoryRecyclerView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(cxt, LinearLayoutManager.HORIZONTAL, false);

        public VenueCategoryViewHolder(final View itemView) {
            super(itemView);

            categoryTitle = itemView.findViewById(R.id.parent_item_title);
            venueCategoryRecyclerView = itemView.findViewById(R.id.child_recyclerview);
            venueCategoryRecyclerView.setNestedScrollingEnabled(false);


            venueCategoryRecyclerView.setLayoutManager(layoutManager);
        }
    }

    @NonNull
    @Override
    public VenueCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_object, viewGroup, false);
        return new VenueCategoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull VenueCategoryViewHolder venueCategoryViewHolder, int position) {
        VenueCategory venueCategory = venueCategories.get(position);
        venueCategoryViewHolder.categoryTitle.setText(venueCategory.getVenueCategory());


//        layoutManager.setInitialPrefetchItemCount(venueCategory.getVenueObjectList().size());
//        venueCategoryViewHolder.venueCategoryRecyclerView.setLayoutManager(layoutManager);

        venueObjectAdapter = new VenueObjectAdapter(venueCategory.getVenueObjectList(), cxt);
        venueCategoryViewHolder.venueCategoryRecyclerView.setAdapter(venueObjectAdapter);

        venueCategoryViewHolder.venueCategoryRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
