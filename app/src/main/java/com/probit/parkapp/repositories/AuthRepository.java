package com.probit.parkapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.probit.parkapp.R;
import com.probit.parkapp.common.Callback;
import com.probit.parkapp.model.User;

import java.util.Map;

public class AuthRepository {
    // TODO: cambiar strings de errores a strings.xml

    private static final String TAG = "AuthRepository";

    public static Boolean isUserLoggedIn() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }

    public static String getUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    public static void signup(String email, String pass,
            Callback.OnSuccess onSuccess,
            Callback.OnFailure onFailure) {

        if (email.isEmpty() || pass.isEmpty()) {
            onFailure.run(R.string.api_error_campos_vacios);
            return;
        }

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
//                            onSuccess.run("Success");
                            User newUser = new User(task.getResult().getUser());
                            updateCreateUser(newUser, onSuccess, onFailure);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            onFailure.run(R.string.api_error_fallo_signup);
                        }
                    }
                });
    }

    public static void login(String email, String pass,
                      Callback.OnSuccess onSuccess,
                      Callback.OnFailure onFailure) {

        if (email.isEmpty() || pass.isEmpty()) {
            onFailure.run(R.string.api_error_campos_vacios);
            return;
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "loginEmailAndPass:success");

                    // TODO: esto no deberia llegar a PROD porque aumentaría innecesariamente las lecturas a Firestore y se cobran.
//                    onSuccess.run("Success");
                    User newUser = new User(task.getResult().getUser());
                    updateCreateUser(newUser, onSuccess, onFailure);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "loginEmailAndPass:failure", task.getException());
                    onFailure.run(R.string.api_error_error_autenticacion);
                }
            }
        });
    }

    public static void signOut(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

    private static void updateCreateUser(User user, Callback.OnSuccess onSuccess, Callback.OnFailure onFailure) {

        String userId = user.getId();
        Map<String, Object> userHash = user.getHashForFirestore();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Add a new document with a generated ID
        db.collection("users").document(userId)
                .set(userHash)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot updated. ");
                        onSuccess.run(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        onFailure.run("Error adding document");
                    }
                });
    }

    /**
     * Pasa el usuario como parametro a onSuccess si lo trae bien de la BD.
     */
    public static void getCurrentUser(Callback.OnSuccess onSuccess, Callback.OnFailure onFailure) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String userId = mAuth.getCurrentUser().getUid();
        db.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        onSuccess.run(new User(document));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}
