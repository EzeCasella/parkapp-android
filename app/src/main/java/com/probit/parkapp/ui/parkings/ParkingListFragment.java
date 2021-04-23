package com.probit.parkapp.ui.parkings;

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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.probit.parkapp.R;
import com.probit.parkapp.model.Parking;
import com.probit.parkapp.repositories.ParkingsRepository;
import com.probit.parkapp.ui.SignupFragment;
import com.probit.parkapp.ui.parkings.ParkingListAdapter;
import com.probit.parkapp.ui.parkings.ParkingListViewModel;

import java.util.ArrayList;

public class ParkingListFragment extends Fragment {

    private static final String TAG = "ParkingListFragment";

    private ParkingListViewModel parkingListViewModel;
    private ParkingListAdapter parkingListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        parkingListViewModel =
                new ViewModelProvider(this).get(ParkingListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_parking_list, container, false);

        RecyclerView parkingListRecycler = root.findViewById(R.id.parking_list_recycler);
        parkingListAdapter = new ParkingListAdapter();
        parkingListRecycler.setAdapter(parkingListAdapter);
        parkingListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

//        NavHostFragment.findNavController(ParkingListFragment.this).popBackStack();



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        parkingListViewModel.getParkingsLiveData().observe(getViewLifecycleOwner(), parkingListAdapter::submitList);
        parkingListViewModel.fetchParkings(this::handleError);



    }

    private void handleError(Object error){
        Toast.makeText(requireActivity(), error.toString() , Toast.LENGTH_LONG).show();
    }

//    private void logParkings() {
//
//        ParkingsRepository.getParkings(data -> {
//            ArrayList<Parking> parkings = (ArrayList<Parking>) data;
//            for (Parking park : parkings) {
//                Log.i(TAG, park.getName());
//            }
//        }, error -> Toast.makeText(requireActivity(), error.toString() , Toast.LENGTH_LONG).show());
//    }

}
