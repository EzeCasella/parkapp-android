package com.probit.parkapp.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Parking {
    private String id;
    private final String name;
    private final String latitude;
    private final String longitude;
    private final String address;
    private final String phoneNumber;
    private final String carSlots;
    private final String hourRate;
    private final String openingHours;
    private final String notes;

    public Parking(String name, String latitude, String longitude, String address,
                   String phoneNumber, String carSlots, String hourRate, String openingHours, String notes) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.carSlots = carSlots;
        this.hourRate = hourRate;
        this.openingHours = openingHours;
        this.notes = notes;
    }

    public Parking(QueryDocumentSnapshot parking) {
        this.id = parking.getId();
        this.name = parking.get("name").toString();
        this.latitude = parking.get("latitude").toString();
        this.longitude = parking.get("longitude").toString();
        this.address = parking.get("address").toString();
        this.phoneNumber = parking.get("phoneNumber").toString();
        this.carSlots = parking.get("carSlots").toString();
        this.hourRate = parking.get("hourRate").toString();
        this.openingHours = parking.get("openingHours").toString();
        this.notes = parking.get("notes").toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCarSlots() {
        return carSlots;
    }

    public String getHourRate() {
        return hourRate;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public String getNotes() {
        return notes;
    }
}
