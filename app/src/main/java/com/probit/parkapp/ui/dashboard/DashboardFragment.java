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

import com.google.firebase.Timestamp;
import com.probit.parkapp.R;
import com.probit.parkapp.model.Parking;
import com.probit.parkapp.model.User;
import com.probit.parkapp.repositories.AuthRepository;
import com.probit.parkapp.repositories.ParkingsRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
                logCurrentUser();
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

//        SchedulesRepository.createSchedule();

        Date dt = new Date();
        //Initialize your Date however you like it.
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
//Add one to month {0 - 11}
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Timestamp ts = new Timestamp(dt);
        Log.i(TAG, dt.toString());
        Log.i(TAG,ts.toString());
        Log.i(TAG, "Dia: "+datePicker.getDayOfMonth() + ", Anio: "+ datePicker.getYear());

//        TimePicker tp;
//        tp.get

//        Date dtt = new SimpleDateFormat("")
    }
}