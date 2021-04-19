package com.probit.parkapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.probit.parkapp.common.Callback;
import com.probit.parkapp.model.Schedule;

import java.util.ArrayList;
import java.util.Map;

public class SchedulesRepository {

    private static final String TAG = "SchedulesRepository";
    private static final String SCHEDULES_COLLECTION = "schedules";

    public static void createSchedule(Schedule schedule, Callback.OnSuccess onSuccess, Callback.OnFailure onFailure) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> scheduleHash = schedule.getHashForFirestore();

        db.collection(SCHEDULES_COLLECTION)
                .add(scheduleHash)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        onSuccess.run(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = "Error al hacer la reserva";
                        Log.w(TAG, error, e);
                        onFailure.run(error);
                    }
                });
    }

    public static void getUserSchedules(Callback.OnSuccess<ArrayList<Schedule>> onSuccess, Callback.OnFailure onFailure) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(SCHEDULES_COLLECTION).whereEqualTo("userId", AuthRepository.getUserId()).orderBy("checkinDate", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Schedule> schedules = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Schedule schedule = new Schedule(document);
                                schedules.add(schedule);
                            }
                            onSuccess.run(schedules);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            onFailure.run("Error getting documents.");
                        }
                    }
                });
    }
}
