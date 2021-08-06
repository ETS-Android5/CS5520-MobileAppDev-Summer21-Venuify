package edu.neu.venuify;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import edu.neu.venuify.Models.VenueObject;


public class QR_Activity extends BaseActivity {
    private final int PERMISSION_REQUEST_CAMERA = 42;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCamera();

    }

    private boolean hasCameraPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startCameraPermissionRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CAMERA);
    }

    private void requestCamera() {
        if (hasCameraHardware(this)) {
            if (hasCameraPermissions()) {
                startCamera();
            }
            else {
                startCameraPermissionRequest();
            }
        }
        else {
            Toast.makeText(this, "No Camera hardware detected. A camera is required to scan QR codes.", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCamera() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }


    private boolean hasCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Intent openVenueDetails = new Intent(getApplicationContext(), VenueDetailsPage.class);
                VenueObject venueObject = parseQRCode(intentResult);
                openVenueDetails.putExtra("venue", venueObject);
                startActivity(openVenueDetails);
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
