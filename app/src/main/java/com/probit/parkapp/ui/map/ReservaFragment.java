package com.probit.parkapp.ui.map;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
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
import com.google.type.DateTime;
import com.probit.parkapp.R;
import com.probit.parkapp.model.Parking;
import com.probit.parkapp.model.Schedule;
import com.probit.parkapp.repositories.SchedulesRepository;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    String parkingId;
    String nombreParking;
    String direccion;
    String telefono;
    String horario;
    String lugaresDisponibles;
    String notas;
    String latitud;
    String longitud;
    String precio;



    Date entrada;
    Date salida;

    public ReservaFragment(){

    }

    public ReservaFragment(Parking pk){
        parkingId          = pk.getId();
        nombreParking      = pk.getName();
        direccion          = pk.getAddress();
        telefono           = pk.getPhoneNumber();
        horario            = pk.getOpeningHours();
        lugaresDisponibles = pk.getCarSlots();
        notas              = pk.getNotes();
        latitud            = pk.getLatitude();
        longitud           = pk.getLongitude();
        precio             = pk.getHourRate();


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
//                Toast.makeText(getContext(), "Reservo parking", Toast.LENGTH_SHORT).show();

                String entradaAux = String.valueOf(dateFrom.getText());
                entradaAux = entradaAux + String.valueOf(timeFrom.getText());
                String salidaAux  = String.valueOf(dateTo.getText());
                salidaAux = salidaAux + timeTo.getText();


                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyyHH:mm");
                try {
                    entrada = sdf.parse(entradaAux);
                    salida  = sdf.parse(salidaAux);

                    if(salida.after(entrada)){
                        confirmaReserva();
                    }else{
                        Toast.makeText(getContext(), "La fecha y hora de salida debe ser posterior a la de entrada", Toast.LENGTH_LONG).show();
                    }


                } catch (Exception ex) {
                    Log.i("DateFromatException", ex.getLocalizedMessage());
                }



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


    private void createSchedule() {
//        Calendar calendar = Calendar.getInstance();
//
//        Date checkoutDate = calendar.getTime();
//        calendar.add(Calendar.HOUR, -1);
//        Date checkInDate = calendar.getTime();

        String parkingId = "3fJpMfTlK8MxWoskLdKH"; // La idea es que solamente se creen reservas
        // con los parkings que ya estan en la base, por eso no se usa este campo ahora, el id se
        // deberia sacar del mismo objeto almacenado en la base de datos

        Parking park = new Parking(nombreParking, latitud, longitud, direccion,
                telefono,lugaresDisponibles,precio,horario,notas);

        Schedule schedule = new Schedule(
                park,
                entrada,
                salida
        );
        SchedulesRepository.createSchedule(schedule, data -> {
            Toast.makeText(requireActivity(), "RESERVA CREADA CON ÉXITO", Toast.LENGTH_LONG).show();
            dismiss();
        }, error -> {
            Toast.makeText(requireActivity(), error.toString(), Toast.LENGTH_LONG).show();
        });
    }

    private void confirmaReserva() {
        new AlertDialog.Builder(getContext())
//                .setIcon(R.drawable.alacran)
                .setTitle("¿Está seguro que quiere realizar la reserva?")
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        createSchedule();
                        Toast.makeText(requireActivity(), "SIMULACIÓN DE RESERVA CREADA CON ÉXITO", Toast.LENGTH_LONG).show();
                    }
                }).show();
    }


}