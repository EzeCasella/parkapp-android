package com.probit.parkapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Toast;

import com.probit.parkapp.model.User;
import com.probit.parkapp.repositories.AuthRepository;

public class LoginSignupActivity extends AppCompatActivity {

    private static final String TAG = "LoginSignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_signup);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        if(AuthRepository.isUserLoggedIn()){
            Toast.makeText(LoginSignupActivity.this, "Usuario LOGUEADO",
                    Toast.LENGTH_SHORT).show();
            navigateToMainActivity();
        } else {
            Toast.makeText(LoginSignupActivity.this, "NO LOGUEADO",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}