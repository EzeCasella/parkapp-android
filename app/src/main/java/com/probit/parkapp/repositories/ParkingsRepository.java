package com.probit.parkapp.repositories;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.probit.parkapp.common.Callback;
import com.probit.parkapp.model.Parking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParkingsRepository {

    private static final String TAG = "ParkingsRepository";

     public static void getParkings(Callback.OnSuccess onSuccess, Callback.OnFailure onFailure) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("parkings")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Parking> parks = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Parking parking = new Parking(document);
                                parks.add(parking);
                            }
                            onSuccess.run(parks);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            onFailure.run("Error getting documents.");
                        }
                    }
                });
    }

}
