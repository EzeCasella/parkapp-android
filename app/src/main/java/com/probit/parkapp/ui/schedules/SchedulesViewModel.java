package com.probit.parkapp.ui.schedules;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.probit.parkapp.common.Callback;
import com.probit.parkapp.model.Schedule;
import com.probit.parkapp.repositories.SchedulesRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class SchedulesViewModel extends ViewModel {

    private MutableLiveData<ArrayList<SchedulesListItem>> mSchedulesAndTitles = new MutableLiveData<>();
//    private ArrayList<Schedule> schedulesCached =
    private String onGoingSectionTitle;
    private String pastSectionTitle;

    public SchedulesViewModel(){}
    public void setSectionsTitles(String onGoingSectionTitle, String pastSectionTitle){
        this.onGoingSectionTitle = onGoingSectionTitle;
        this.pastSectionTitle = pastSectionTitle;
    }

    public LiveData<ArrayList<SchedulesListItem>> getSchedulesAndTitlesLiveData() {
        return mSchedulesAndTitles;
    }

    public void fetchSchedules(Callback.OnFailure onFailure){
//        FETCH de las reservas
        SchedulesRepository.getUserSchedules(this::sortAndShow,onFailure);
    }

    private void sortAndShow(ArrayList<Schedule> scheds) {

        if (scheds.isEmpty()) return;

        ArrayList<SchedulesListItem> results = new ArrayList<>();
        Date now = new Date();

        // Filter 'on going' schedules
        int i = 0;
        while ( i < scheds.size() && scheds.get(i).getCheckinDate().compareTo(now) > 0) {
                results.add(scheds.get(i));
                i+=1;
//                schedule = scheds.get(i);
        }

        // Query ordered by checkin descendant
        results.add(new SchedulesListTitle(onGoingSectionTitle));
        Collections.reverse(results);

        // Filter 'past' schedules
        results.add(new SchedulesListTitle(pastSectionTitle));
        while (i < scheds.size()) {
            results.add(scheds.get(i));
            i+=1;
        }


        mSchedulesAndTitles.setValue(results);
    }

}