package edu.neu.venuify.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import edu.neu.venuify.Entities.User;
import edu.neu.venuify.HomePage;
import edu.neu.venuify.MainActivity;
import edu.neu.venuify.R;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText user_first_name;
    private EditText user_last_name;
    private EditText user_email;
    private EditText user_password1;
    private EditText user_password2;
    private CheckBox user_isAdmin;
    private Button registerBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        setUserInput();
        registerBtn = findViewById(R.id.register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        registerBtn.setOnClickListener(view -> {
            createUser();
        });

        EditText password2 = findViewById(R.id.password2);
        password2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v.getId() == R.id.password2 && !hasFocus) {
                    InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
    }

    //Initiates creating the user both in Firebase authentication and a corresponding entry in the
    //"users" document in the database.  Takes user to homepage if all actions successful
    private void createUser() {
        String firstName = user_first_name.getText().toString();
        String lastName = user_last_name.getText().toString();
        String email  = user_email.getText().toString();
        String password1 = user_password1.getText().toString();
        String password2 = user_password2.getText().toString();
        Boolean isAdmin = user_isAdmin.isChecked();

        if (validateUserInfo(firstName, lastName, email, password1, password2)) {
            mAuth.createUserWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser loggedInUser = mAuth.getCurrentUser();
                                writeNewUser(loggedInUser, firstName, lastName, isAdmin);
                            } else {
                                Toast.makeText(RegisterUserActivity.this, "Error creating account" + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    //Writes user to "users" document in database and redirects user to MainActivity if successful
    //Uid stored in user entry corresponds to the uid of the user in firebase authentication
    //User signed in and taken to homepage if successful
    private void writeNewUser(FirebaseUser loggedInUser, String firstName, String lastName, Boolean adminFlag) {
        String id = mDatabase.push().getKey();
        User user = new User(loggedInUser.getUid(), firstName, lastName, adminFlag);


        mDatabase.child("users").child(id).setValue(user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(RegisterUserActivity.this, "User created and signed in successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterUserActivity.this, HomePage.class));
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterUserActivity.this, "Account created in Firebase auth, but error writing to user table", Toast.LENGTH_SHORT).show();
                }
            });
    }

    //Checks to make sure no fields are blank and that passwords match.  Returns true if all validation checks pass, false otherwise
    private boolean validateUserInfo(String firstName, String lastName, String email, String password1, String password2) {
        if (TextUtils.isEmpty(firstName)) {
            user_first_name.setError("Please enter your first name");
            user_first_name.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(lastName)) {
            user_last_name.setError("Please enter your last name");
            user_last_name.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            user_email.setError("Please enter an email address");
            user_email.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(password1)) {
            user_password1.setError("Please enter a password");
            user_password1.requestFocus();
            return false;
        }
        else if (!password1.equals(password2)) {
            user_password2.setError("Passwords do not match");
            user_password2.requestFocus();
            return false;
        }
        return true;
    }

    private void setUserInput() {
        user_first_name = findViewById(R.id.first_name);
        user_last_name = findViewById(R.id.last_name);
        user_email = findViewById(R.id.email);
        user_password1 = findViewById(R.id.password1);
        user_password2 = findViewById(R.id.password2);
        user_isAdmin = findViewById((R.id.chkAdmin));
    }
}