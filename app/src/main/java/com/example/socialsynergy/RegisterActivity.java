package com.example.socialsynergy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    // Views
    EditText email_et, password_et;
    Button register_btn;

    // Display Progressbar
    AlertDialog progress_dialog;

    // Declaring Instance of FirebaseAuth
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        // Actionbar and Title
        ActionBar action_bar = getSupportActionBar();
        action_bar.setTitle("Create Account");

        // Back Button
        action_bar.setDisplayHomeAsUpEnabled(true);
        action_bar.setDisplayShowHomeEnabled(true);

        // Init
        email_et = findViewById(R.id.emailEt);
        password_et = findViewById(R.id.passwordEt);
        register_btn = findViewById(R.id.registerBtn);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Creating a ProgressBar and Building Dialog
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(progressBar);
        builder.setMessage("Registering User...");

        progress_dialog = builder.create();

        progress_dialog.show();


        // Handles Register Activity
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Input Email and Password
                String email = email_et.getText().toString().trim();
                String password = password_et.getText().toString().trim();
                // Validate
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // Set Error and Focus to Email EditText
                    email_et.setError("Invalid Email");
                    email_et.setFocusable(true);
                } else if (password.length() < 6) {
                    // Set Error and Focus to Email EditText
                    password_et.setError("Password needs to be at least 6 characters long");
                    password_et.setFocusable(true);
                }
                else {
                    registerUser(email, password); // Register the User
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void registerUser(String email, String password) {
        progress_dialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, dismiss dialog and start register activity
                            progress_dialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            progress_dialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error
                        progress_dialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            } // onBackPressed() was deprecated so updated
        });
        return super.onSupportNavigateUp();
    }
}