package edu.neu.venuify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.neu.venuify.Adapters.VenueObjectAdapter;
import edu.neu.venuify.Models.VenueObject;


public class SearchActivity extends BaseActivity {
    private VenueObjectAdapter venueObjectAdapter;
    private List<VenueObject> results  = new ArrayList<>();

    ActivityResultLauncher<Intent> enterSearchQueryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult activityResult) {
                    if (activityResult.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent intent = Objects.requireNonNull(activityResult.getData());
                        results = intent.getParcelableArrayListExtra("data");
                        venueObjectAdapter.setVenueObjectList(results);
                        venueObjectAdapter.notifyDataSetChanged();
                    }
                    else if (activityResult.getResultCode() == EnterSearchQuery.NO_RESULTS){
                        venueObjectAdapter.clearVenueObjectList(); //clear any previous results
                        Toast toast = Toast.makeText(SearchActivity.this, "No Results Found", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                }

            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView searchRecyclerView = findViewById(R.id.searchRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        venueObjectAdapter = new VenueObjectAdapter(results);
        searchRecyclerView.setAdapter(venueObjectAdapter);
        searchRecyclerView.setLayoutManager(gridLayoutManager);
    }



    @Override
    public int getContentViewId() { return R.layout.activity_search; }

    @Override
    public int getNavigationMenuItemId() { return R.id.nav_bar_search; }


    public void startEnterSearchQueryActivity(View view) {
        enterSearchQueryLauncher.launch(new Intent(this, EnterSearchQuery.class));
    }

}
