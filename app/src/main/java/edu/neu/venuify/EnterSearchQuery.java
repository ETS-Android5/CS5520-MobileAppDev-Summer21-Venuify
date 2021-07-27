package edu.neu.venuify;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.neu.venuify.Adapters.VenueObjectAdapter;
import edu.neu.venuify.Models.VenueObject;

public class EnterSearchQuery extends AppCompatActivity {
    private VenueObjectAdapter venueObjectAdapter;
//    private ProgressBar progressBar;
    private final List<VenueObject> results = new ArrayList<>();
    private final String STOPSTRING = "\\uf8ff";


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_search_query);
        setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        progressBar = findViewById(R.id.progressBar);
        RecyclerView searchRecyclerView = findViewById(R.id.searchRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        venueObjectAdapter = new VenueObjectAdapter(results);
        searchRecyclerView.setAdapter(venueObjectAdapter);
        searchRecyclerView.setLayoutManager(linearLayoutManager);
        handleIntent(getIntent());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_query, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_menu_item).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        return true;
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

    private void doSearch(String venueQuery) {
//        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Venues");
        databaseReference.orderByChild("VenueName").startAt(venueQuery).endAt(STOPSTRING).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                results.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String venueName = postSnapshot.child("VenueName").getValue(String.class);
                    if (venueQuery.equalsIgnoreCase(venueName) || Objects.requireNonNull(venueName).toLowerCase().contains(venueQuery.toLowerCase())) {
                        Integer imageID = Objects.requireNonNull(postSnapshot.child("ImageID").getValue(Integer.class));
                        VenueObject venueObject = new VenueObject(venueName, imageID);
                        results.add(venueObject);
                    }
                }
                venueObjectAdapter.setVenueObjectList(results);
                venueObjectAdapter.notifyDataSetChanged();
//                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
