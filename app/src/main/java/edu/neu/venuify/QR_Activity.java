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
                if (venueObject.getVenueName().equals("") || venueObject.getImageId() == 0) {
                    Toast toast = Toast.makeText(getBaseContext(), "Error reading QR code.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                else {
                    openVenueDetails.putExtra("venue", venueObject);
                    startActivity(openVenueDetails);
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
            int imageId = jsonObject.getInt("ImageId");
            return new VenueObject(name, imageId);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return new VenueObject("", 0);
    }



    @Override
    public int getContentViewId() {
        return R.layout.activity_qr_reader;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.nav_bar_qr;
    }

}
