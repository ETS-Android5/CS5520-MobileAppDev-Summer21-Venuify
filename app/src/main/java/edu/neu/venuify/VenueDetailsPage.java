package edu.neu.venuify;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class VenueDetailsPage extends AppCompatActivity {
    public Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venue_details_page);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
   //   Drawable image = bundle.getParcelable("image");

        TextView venueTitleOnDetailsPage = findViewById(R.id.venueTitleOnDetailsPg);
        venueTitleOnDetailsPage.setText(title);
        ImageView venueImgOnDetailsPage = findViewById(R.id.venueImgOnDetailsPage);
      //  venueImgOnDetailsPage.setImageDrawable(image);


        bookButton = findViewById(R.id.bookNowButton);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = 0;

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Book Reservation");

                LinearLayout layout = new LinearLayout(v.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                builder.setView(layout);

                // Set up the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //TODO: Clicking "yes" should create the reservation instance
                        // in the database, and take user to reservationDetailsPage
                        // with correct information displayed

                        Intent i = new Intent(v.getContext(), ReservationDetailsPage.class);
                        startActivity(i);

                    }


                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }

        });
    }
}
