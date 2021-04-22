package com.probit.parkapp.model;

import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.probit.parkapp.repositories.AuthRepository;
import com.probit.parkapp.ui.schedules.SchedulesListItem;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Schedule implements FirestoreEntity, SchedulesListItem {

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

        long millisEntrada = checkinDate.getTime();
        long millisSalida  = checkoutDate.getTime();
        long diffMillis = millisSalida - millisEntrada;
        long tiempoMinutos = diffMillis / 1000 / 60;
        float precio = Float.parseFloat(parkingHourRate);
        float cost = precio * tiempoMinutos / 60;

        // Redondea a dos decimales
        cost = (float) (Math.round(cost * 100.0) / 100.0);

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

    private static final String PARKING_KEY = "parkingId";
    private static final String USER_KEY = "userId";
    private static final String CHECKIN_KEY = "checkinDate";
    private static final String CHECKOUT_KEY = "checkoutDate";
    private static final String PARKING_NAME_KEY = "parkingName";
    private static final String PARKING_ADDRESS_KEY = "parkingAddress";
    private static final String PARKING_RATE_KEY = "parkingHourRate";

    private static final String DELETED_KEY = "deleted";

    public Schedule (DocumentSnapshot schedule) {
        this(schedule.getId(),
                schedule.get(PARKING_KEY).toString(),
                schedule.get(USER_KEY).toString(),
                schedule.getTimestamp(CHECKIN_KEY).toDate(),
                schedule.getTimestamp(CHECKOUT_KEY).toDate(),
                schedule.get(PARKING_NAME_KEY).toString(),
                schedule.get(PARKING_ADDRESS_KEY).toString(),
                schedule.get(PARKING_RATE_KEY).toString());
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
        scheduleHash.put(PARKING_KEY, this.getParkingId());
        scheduleHash.put(USER_KEY, this.getUserId());
        scheduleHash.put(CHECKIN_KEY, new Timestamp(this.getCheckinDate()));
        scheduleHash.put(CHECKOUT_KEY, new Timestamp(this.getCheckoutDate()));
        scheduleHash.put(PARKING_NAME_KEY, this.getParkingName());
        scheduleHash.put(PARKING_ADDRESS_KEY, this.getParkingAddress());
        scheduleHash.put(PARKING_RATE_KEY, this.getParkingHourRate());

        scheduleHash.put(DELETED_KEY, false);

        return scheduleHash;
    }
}
