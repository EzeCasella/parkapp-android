package com.probit.parkapp.ui.parkings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.probit.parkapp.common.Callback;
import com.probit.parkapp.model.Parking;
import com.probit.parkapp.repositories.ParkingsRepository;

import java.util.ArrayList;

public class ParkingListViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Parking>> mParkings = new MutableLiveData<>();

    public ParkingListViewModel(){}


    public LiveData<ArrayList<Parking>> getParkingsLiveData() {
        return mParkings;
    }

    public void fetchParkings(Callback.OnFailure onFailure){
//        FETCH de los parkings
        ParkingsRepository.getParkings(this::show, onFailure);
    }



    private void show(ArrayList<Parking> park) {

        if (park.isEmpty()) return;

        ArrayList<Parking> results = new ArrayList<>();
        int i = 0;
        while (i < park.size()) {
            results.add(park.get(i));
            i+=1;
        }


        mParkings.setValue(results);
    }
}
