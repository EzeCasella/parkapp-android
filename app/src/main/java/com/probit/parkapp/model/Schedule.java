package com.probit.parkapp.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.probit.parkapp.repositories.AuthRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Schedule implements FirestoreEntity {

    // Self fields
    private String id;
    private final String parkingId;
    private final String userId;
    private final Date checkinDate;
    private final Date checkoutDate;

    // Calculated
    private Float cost;

//    No en uso aun
//    private final Boolean confirmed;

    // Fields for Firestore usage optimization
    private final String parkingName;
    private final String parkingAddress;
    private final String parkingHourRate;

    public String getId() {
        return id;
    }

    public String getParkingId() {
        return parkingId;
    }

    public String getUserId() {
        return userId;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public Float getCost() {
        /*
         * TODO: Implementar en base a checkinDate, checkoutDate y hourRate
         * */
        return cost;
    }

    public String getParkingName() {
        return parkingName;
    }

    public String getParkingAddress() {
        return parkingAddress;
    }

    public String getParkingHourRate() {
        return parkingHourRate;
    }

    public Schedule( Parking parking, Date checkinDate, Date checkoutDate) {
        this(parking.getId(), checkinDate, checkoutDate,
                parking.getName(), parking.getAddress(), parking.getHourRate());
    }

    public Schedule (DocumentSnapshot schedule) {
        this(schedule.getId(),
                schedule.get("parkingId").toString(),
                schedule.get("userId").toString(),
                schedule.getTimestamp("checkinDate").toDate(),
                schedule.getTimestamp("checkoutDate").toDate(),
                schedule.get("parkingName").toString(),
                schedule.get("parkingAddress").toString(),
                schedule.get("parkingHourRate").toString());
    }

    private Schedule(String parkingId, Date checkinDate, Date checkoutDate, String parkingName, String parkingAddress, String parkingHourRate) {
        this("NOT_VALID",parkingId ,
                AuthRepository.getUserId(), // userId
                checkinDate ,checkoutDate ,parkingName ,parkingAddress ,parkingHourRate);
    }

    private Schedule (String id,String parkingId, String userId, Date checkinDate, Date checkoutDate, String parkingName, String parkingAddress, String parkingHourRate) {
        this.id = id;
        this.parkingId = parkingId;
        this.userId = userId;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.parkingName = parkingName;
        this.parkingAddress = parkingAddress;
        this.parkingHourRate = parkingHourRate;
    }

    /*
     * Changing keys here will change attributes in Firestore.
     * */
    @Override
    public Map<String, Object> getHashForFirestore(){

        Map<String, Object> scheduleHash = new HashMap<>();
        scheduleHash.put("parkingId", this.getParkingId());
        scheduleHash.put("userId", this.getUserId());
        scheduleHash.put("checkinDate", new Timestamp(this.getCheckinDate()));
        scheduleHash.put("checkoutDate", new Timestamp(this.getCheckoutDate()));
        scheduleHash.put("parkingName", this.getParkingName());
        scheduleHash.put("parkingAddress", this.getParkingAddress());
        scheduleHash.put("parkingHourRate", this.getParkingHourRate());

        return scheduleHash;
    }
}
