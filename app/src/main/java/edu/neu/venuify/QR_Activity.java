package edu.neu.venuify;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import org.json.JSONException;
import org.json.JSONObject;

import edu.neu.venuify.Models.VenueObject;


public class QR_Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void launchScan(View view) {
        new IntentIntegrator(this).setCaptureActivity(ScanQR.class).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Intent openVenueDetails = new Intent(getApplicationContext(), VenueDetailsPage.class);
                VenueObject venueObject = parseQRCode(intentResult);
                if (venueObject.getVenueName().equals("") && venueObject.getImageId().equals("")) {
                   makeCenteredToast("Error reading QR code.");
                }
                else {
                    if (venueExistsInDatabase(venueObject)) {
                        openVenueDetails.putExtra("venue", venueObject);
                        startActivity(openVenueDetails);
                    }
                    else {
                        makeCenteredToast("No Venue found matching this QR code.");
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private VenueObject parseQRCode(IntentResult intentResult) {
        try {
            JSONObject jsonObject = new JSONObject(intentResult.getContents());
            String name = jsonObject.getString("VenueName");
            String category = jsonObject.getString("Category");
            String imageId = jsonObject.getString("ImageId");
            return new VenueObject(name, category, imageId);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return new VenueObject("", "", "");
    }

    private boolean venueExistsInDatabase(VenueObject venueObject) {
      return Utils.venueMap.containsValue(venueObject.getVenueName());
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_qr_reader;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.nav_bar_qr;
    }

    private void makeCenteredToast(String message) {
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

}
