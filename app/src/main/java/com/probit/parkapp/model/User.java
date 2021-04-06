package com.probit.parkapp.model;

import com.google.firebase.auth.FirebaseUser;

public class User {

    public String id;
    public String email;

    public String getEmail() {
        return email;
    }

    public User(String id) {
        this.id = id;
    }

    public User(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            this.id = firebaseUser.getUid();
            this.email = firebaseUser.getEmail();
        };
    }


    public String getId() {
        return id;
    }
}
