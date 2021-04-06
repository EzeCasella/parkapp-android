package com.probit.parkapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.probit.parkapp.common.Callback;
import com.probit.parkapp.model.User;

import org.jetbrains.annotations.Nullable;

public class AuthRepository {
    // TODO: cambiar strings de errores a strings.xml

    private static final String TAG = "AuthRepository";

    @Nullable
    public static User getCurrentUser() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            return new User(currentUser);
        }
        return null;
    }

    public static void signup(String email, String pass,
            Callback.OnSuccess onSuccess,
            Callback.OnFailure onFailure) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(
                email,
                pass)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            onSuccess.run(getCurrentUser());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            onFailure.run("Falló el signup.");
                        }
                    }
                });
    }

    public static void login(String email, String pass,
                      Callback.OnSuccess onSuccess,
                      Callback.OnFailure onFailure) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "loginEmailAndPass:success");
                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
                    Log.i(TAG, user.getEmail());
                    onSuccess.run(getCurrentUser());
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "loginEmailAndPass:failure", task.getException());
                    onFailure.run("Error de autenticación.");
                }
            }
        });
    }

    public static void signOut(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }
}
