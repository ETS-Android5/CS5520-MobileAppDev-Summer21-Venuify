package edu.neu.venuify;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import edu.neu.venuify.Models.VenueObject;

public class EnterSearchQuery extends AppCompatActivity {
    private final ArrayList<VenueObject> results = new ArrayList<>();
    private final String STOPSTRING = "\\uf8ff";
    public static final int NO_RESULTS = -255;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_search_query);
        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setTitle("");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        handleIntent(getIntent());

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Venues");
        databaseReference.orderByChild("VenueName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                results.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String venueName = Objects.requireNonNull(postSnapshot.child("VenueName").getValue(String.class));
                    if (venueQuery.equalsIgnoreCase(venueName) || venueName.toLowerCase().contains(venueQuery.toLowerCase())) {
                        Integer imageID = Objects.requireNonNull(postSnapshot.child("ImageID").getValue(Integer.class));
                        VenueObject venueObject = new VenueObject(venueName, imageID);
                        results.add(venueObject);
                    }
                }


                if (results.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    intent.putParcelableArrayListExtra("data", results);
                    setResult(Activity.RESULT_OK, intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    setResult(NO_RESULTS, intent);
                }

                onBackPressed();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
