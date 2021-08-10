package edu.neu.venuify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import edu.neu.venuify.Authentication.LoginActivity;
import edu.neu.venuify.Entities.User;

public class AccountPageActivity extends AppCompatActivity {

    private Button logOutBtn;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings_page);

        mAuth = FirebaseAuth.getInstance();

        String currentUser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();



        mDatabase.child("users").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                        User user = Objects.requireNonNull(snapshot.getValue(User.class));


                        //before add reservations to recycler view for person, need to check the user
                        String currentUserUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                        //if the user id is equal the user id listed under the reservation, display it
                        if (currentUserUid.equals(user.getUid())) {


                            //get reservation date and send to function to evaluate if in past
                            String userFirstName = user.getFirstName();
                            String userLastName = user.getLastName();

                            TextView firstN = findViewById(R.id.firstName);
                            TextView lastN = findViewById(R.id.lastName);

                            firstN.setText(userFirstName);
                            lastN.setText(userLastName);


                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                }
        );






        logOutBtn = findViewById(R.id.logout_btn2);
        logOutBtn.setOnClickListener(view -> {
            mAuth.signOut();
            openLoginPage();
        });
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
