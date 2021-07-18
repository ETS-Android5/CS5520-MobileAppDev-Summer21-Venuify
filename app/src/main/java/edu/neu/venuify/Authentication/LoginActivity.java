package edu.neu.venuify.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.neu.venuify.MainActivity;
import edu.neu.venuify.R;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.button2);

        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(view -> {
            loginUser();
        });
    }

    public void openRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        startActivity(intent);
    }

    private void loginUser() {
        String entered_email = username.getText().toString();
        String entered_password = password.getText().toString();

        if (TextUtils.isEmpty(entered_email)) {
            username.setError("Please enter an email address");
            username.requestFocus();
        }
        else if (TextUtils.isEmpty(entered_password)) {
            password.setError("Please enter a password");
            password.requestFocus();
        }
        else {
            mAuth.signInWithEmailAndPassword(entered_email, entered_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "User logged in successfully!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Login not successful",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}