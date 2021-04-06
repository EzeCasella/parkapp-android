package com.probit.parkapp.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.probit.parkapp.R;
import com.probit.parkapp.model.Parking;
import com.probit.parkapp.repositories.ParkingsRepository;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {


    private static final String TAG = "DashboardFragment";
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button btnLogParkings = root.findViewById(R.id.button_log_parkings);
        btnLogParkings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logParkings();
            }
        });

        return root;
    }

    private void logParkings() {

        ParkingsRepository.getParkings(data -> {
            ArrayList<Parking> parkings = (ArrayList<Parking>) data;
            for (Parking park : parkings) {
                Log.i(TAG, park.getName());
            }
        }, error -> Toast.makeText(requireActivity(), error.toString() , Toast.LENGTH_LONG));
    }
}