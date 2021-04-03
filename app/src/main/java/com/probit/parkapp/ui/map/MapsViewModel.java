package com.probit.parkapp.ui.map;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.google.android.gms.maps.GoogleMap;
import com.probit.parkapp.model.Parking;
import com.probit.parkapp.repositories.ParkingsRepository;

import java.util.ArrayList;

public class MapsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private GoogleMap mText;


//    ParkingsRepository.get(data -> {
//        ArrayList<Parking> parkings = (ArrayList<Parking>) data;
//        for (Parking park : parkings) {
//            Log.i(TAG, park.getName());
//        }
//    });

//    public MapsViewModel() {
//        mMap = new GoogleMap<>();
//        mText.setValue("Pantalla de reservas");
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }
}

