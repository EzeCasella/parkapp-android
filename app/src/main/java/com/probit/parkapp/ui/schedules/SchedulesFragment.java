package com.probit.parkapp.ui.schedules;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.probit.parkapp.R;
import com.probit.parkapp.model.Parking;
import com.probit.parkapp.repositories.ParkingsRepository;

import java.util.ArrayList;

public class SchedulesFragment extends Fragment {


    private static final String TAG = "SchedulesFragment";

    private SchedulesViewModel schedulesViewModel;
    private SchedulesAdapter schedulesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        schedulesViewModel =
                new ViewModelProvider(this).get(SchedulesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView schedulesRecycler = root.findViewById(R.id.schedules_recycler);
        schedulesAdapter = new SchedulesAdapter(position -> schedulesViewModel.onDeleteSchedule(position, this::handleError));
        schedulesRecycler.setAdapter(schedulesAdapter);
        schedulesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        schedulesViewModel.setSectionsTitles(getString(R.string.reservas_corrientes_colon), getString(R.string.reservas_pasadas_colon));
        schedulesViewModel.getSchedulesAndTitlesLiveData().observe(getViewLifecycleOwner(), schedulesAdapter::submitList);
        schedulesViewModel.fetchSchedules(this::handleError);
    }

    private void handleError(Object error){
        Toast.makeText(requireActivity(), error.toString() , Toast.LENGTH_LONG).show();
    }

    private void logParkings() {

        ParkingsRepository.getParkings(data -> {
            ArrayList<Parking> parkings = (ArrayList<Parking>) data;
            for (Parking park : parkings) {
                Log.i(TAG, park.getName());
            }
        }, error -> Toast.makeText(requireActivity(), error.toString() , Toast.LENGTH_LONG).show());
    }
}