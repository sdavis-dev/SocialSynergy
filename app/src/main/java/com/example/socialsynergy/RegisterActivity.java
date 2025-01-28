package com.example.socialsynergy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    // Views
    EditText email_et, password_et;
    Button register_btn;

    // Display Progressbar
    AlertDialog progress_dialog;

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

        // Creating a ProgressBar and Building Dialog
        progress_dialog = new ProgressDialog(this);

        // Handles Register Activity
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
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