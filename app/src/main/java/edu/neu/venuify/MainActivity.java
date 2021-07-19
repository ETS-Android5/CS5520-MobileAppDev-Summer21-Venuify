package edu.neu.venuify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.neu.venuify.reservationPage.ReservationPageActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: remove before merge to master branch
        Intent i = new Intent(this, ReservationPageActivity.class);
        startActivity(i);
    }


}