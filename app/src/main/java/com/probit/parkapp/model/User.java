package com.probit.parkapp.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class User implements FirestoreEntity {

    private static final String PHONE_KEY = "phone" ;
    private static final String NAME_KEY = "name";
    private static final String EMAIL_KEY = "email";
    private static final String VEHICLE_KEY = "vehicle";
    private String id;
    private String email;
    private String name = "";
    private String phone = "";
    private String vehicle = "";

    public User(){}

    public User(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            this.id = firebaseUser.getUid();
            this.email = firebaseUser.getEmail();
        };
    }

    public User(DocumentSnapshot user) {
        this.id = user.getId();
        this.email   = user.get(EMAIL_KEY).toString();
        this.name    = user.get(NAME_KEY).toString();
        this.phone   = user.get(PHONE_KEY).toString();
        this.vehicle = user.get(VEHICLE_KEY).toString();
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

    public String getPhone() {
        return phone;
    }

    public String getVehicle() {
        return vehicle;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setVehicle(String vehicle)  {
        this.vehicle = vehicle;
    }

    /*
    * Changing keys here will change attributes in Firestore.
    * */
    @Override
    public Map<String, Object> getHashForFirestore(){
        Map<String, Object> userHash = new HashMap<>();
        userHash.put(NAME_KEY, this.getName());
        userHash.put(EMAIL_KEY, this.getEmail());
        userHash.put(PHONE_KEY, this.getPhone());
        userHash.put(VEHICLE_KEY, this.getVehicle());

        return userHash;
    }
}
