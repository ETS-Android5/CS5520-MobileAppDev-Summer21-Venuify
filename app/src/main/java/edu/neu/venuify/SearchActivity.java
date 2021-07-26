package edu.neu.venuify;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import edu.neu.venuify.Adapters.VenueObjectAdapter;
import edu.neu.venuify.Models.VenueObject;

public class SearchActivity extends BaseActivity {
    private VenueObjectAdapter venueObjectAdapter;
    private ProgressBar progressBar;
    private final List<VenueObject> results = new ArrayList<>();
    private final String STOPSTRING = "\\uf8ff";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar = findViewById(R.id.progressBar);
        RecyclerView searchRecyclerView = findViewById(R.id.searchRecyclerView);
        venueObjectAdapter = new VenueObjectAdapter(results);
        searchRecyclerView.setAdapter(venueObjectAdapter);
        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }


    @Override
    public int getContentViewId() { return R.layout.activity_search; }

    @Override
    public int getNavigationMenuItemId() { return R.id.nav_bar_search; }



    private void doSearch(String venueName) {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Venues");
        databaseReference.orderByChild("VenueName").startAt(venueName).endAt(STOPSTRING).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                results.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    results.add(postSnapshot.getValue(VenueObject.class));
                }
                venueObjectAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
         });
    }


}
