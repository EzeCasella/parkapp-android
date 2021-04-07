package com.probit.parkapp.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String id;
    private String email;
    private String name = "";

    public User(String id) {
        this.id = id;
    }

    public User(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            this.id = firebaseUser.getUid();
            this.email = firebaseUser.getEmail();
        };
    }

    public User(DocumentSnapshot user) {
        this.id = user.getId();
        this.email = user.get("email").toString();
        this.name = user.get("name").toString();
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    /*
    * Changing keys here will change attributes in Firestore.
    * */
    public Map<String, Object> getUserHashForFirestore(){
        Map<String, Object> userHash = new HashMap<>();
        userHash.put("name", this.getName());
        userHash.put("email", this.getEmail());

        return userHash;
    }
}
