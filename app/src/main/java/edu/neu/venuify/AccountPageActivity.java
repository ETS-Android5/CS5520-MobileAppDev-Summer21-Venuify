package edu.neu.venuify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import edu.neu.venuify.Authentication.LoginActivity;

public class AccountPageActivity extends AppCompatActivity {

    private Button logOutBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings_page);

        mAuth = FirebaseAuth.getInstance();


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
