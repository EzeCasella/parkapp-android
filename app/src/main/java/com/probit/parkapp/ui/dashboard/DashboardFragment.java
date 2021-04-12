package com.probit.parkapp.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.probit.parkapp.R;
import com.probit.parkapp.model.Parking;
import com.probit.parkapp.model.Schedule;
import com.probit.parkapp.model.User;
import com.probit.parkapp.repositories.AuthRepository;
import com.probit.parkapp.repositories.ParkingsRepository;
import com.probit.parkapp.repositories.SchedulesRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DashboardFragment extends Fragment {


    private static final String TAG = "DashboardFragment";

    private DashboardViewModel dashboardViewModel;
    private DatePicker datePicker;

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
//                createSchedule();
//                logCurrentUser();
//                createSchedule();
//                listScheds();
            }
        });

        datePicker = root.findViewById(R.id.dashboard_datepicker);

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

    private void logCurrentUser() {
        AuthRepository.getCurrentUser(user -> {
            String email = ((User) user).getEmail();
            Log.i(TAG, email);
        }, error -> {});
    }

    private void createSchedule() {
        Calendar calendar = Calendar.getInstance();

        Date checkoutDate = calendar.getTime();
        calendar.add(Calendar.HOUR, -1);
        Date checkInDate = calendar.getTime();

        String parkingId = "3fJpMfTlK8MxWoskLdKH"; // La idea es que solamente se creen reservas
        // con los parkings que ya estan en la base, por eso no se usa este campo ahora, el id se
        // deberia sacar del mismo objeto almacenado en la base de datos

        Parking park = new Parking("SALADITO ", "lat", "long", "por alla por dieció",
                "phone","carslots","K-ribe","openingHours","notes");

        Schedule schedule = new Schedule(
                park,
                checkInDate,
                checkoutDate
        );
        SchedulesRepository.createSchedule(schedule, data -> {
            Toast.makeText(requireActivity(), "EXITO CREANDOOO", Toast.LENGTH_LONG).show();
        }, error -> {
            Toast.makeText(requireActivity(), error.toString(), Toast.LENGTH_LONG).show();
        });
    }

    private void listScheds() {
        SchedulesRepository.getUserSchedules(data -> {
            ArrayList<Schedule> scheds = (ArrayList<Schedule>) data;
            for (Schedule sched : scheds) {
                Log.i(TAG, sched.getId());
            }
        }, error -> {
            Toast.makeText(requireActivity(), error.toString() , Toast.LENGTH_LONG);
        });
    }
}