package com.probit.parkapp.ui.map;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.probit.parkapp.R;
import com.probit.parkapp.model.Parking;

import org.w3c.dom.Text;

import java.util.Calendar;

public class ReservaFragment extends BottomSheetDialogFragment {

    private ReservaViewModel mViewModel;
    TextView dateFrom;
    TextView dateTo;
    TextView timeFrom;
    TextView timeTo;

    TextView tvNombreParking;
    TextView tvDireccion;
    TextView tvTelefono;
    TextView tvHorario;
    TextView tvLugaresDisponibles;
    TextView tvNotas;



    DatePickerDialog datePickerDialog;
    TimePicker simpleTimePicker;
    String nombreParking;
    String direccion;
    String telefono;
    String horario;
    String lugaresDisponibles;
    String notas;

    public ReservaFragment(){

    }

    public ReservaFragment(Parking pk){
        nombreParking      = pk.getName();
        direccion          = pk.getAddress();
        telefono           = pk.getPhoneNumber();
        horario            = pk.getOpeningHours();
        lugaresDisponibles = pk.getCarSlots();
        notas              = pk.getNotes();


    }



    public static ReservaFragment newInstance() {
        return new ReservaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.reserva_fragment, container, false);

        dateFrom = (TextView) view.findViewById(R.id.fecha_desde);
        dateTo   = (TextView) view.findViewById(R.id.fecha_hasta);
        timeFrom = (TextView) view.findViewById(R.id.hora_desde);
        timeTo   = (TextView) view.findViewById(R.id.hora_hasta);
        tvNombreParking      = (TextView) view.findViewById(R.id.nombre_parking);
        tvDireccion          = (TextView) view.findViewById(R.id.direccion_parking);
        tvTelefono           = (TextView) view.findViewById(R.id.telefono);
        tvHorario            = (TextView) view.findViewById(R.id.horario);
        tvLugaresDisponibles = (TextView) view.findViewById(R.id.lugares_disponibles);
        tvNotas              = (TextView) view.findViewById(R.id.notas);



        tvNombreParking.setText(nombreParking);
        tvDireccion.setText(direccion);
        tvTelefono.setText(telefono);
        tvHorario.setText(horario);
        tvLugaresDisponibles.setText(lugaresDisponibles);
        tvNotas.setText(notas);


        // perform click event on text view
        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calendar class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dateFrom.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dateTo.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        timeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeFrom.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Seleccione la hora");
                mTimePicker.show();

            }
        });


        timeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTo.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Seleccione la hora");
                mTimePicker.show();

            }
        });

        Button reservarButton = view.findViewById(R.id.reservar_button);
        reservarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Reservo parking", Toast.LENGTH_SHORT).show();







            }
        });


        return view;



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReservaViewModel.class);
        // TODO: Use the ViewModel

    }


}