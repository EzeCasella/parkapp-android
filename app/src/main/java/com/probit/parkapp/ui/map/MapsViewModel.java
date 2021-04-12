package com.probit.parkapp.ui.map;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.probit.parkapp.model.Parking;
import com.probit.parkapp.repositories.ParkingsRepository;

import java.util.ArrayList;

public class MapsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Parking>> mParkings;
    public LiveData<ArrayList<Parking>> getParkingsLiveData() {
        return mParkings;
    }
    @Nullable
    public ArrayList<Parking> getParkings(){
        return mParkings.getValue();
    }

    public MapsViewModel() {
        mParkings = new MutableLiveData<>();;
    }

    public void onMapReady() {
        ParkingsRepository.getParkings(data -> {
            mParkings.setValue((ArrayList<Parking>) data);
        }, data -> {});
    }
}

