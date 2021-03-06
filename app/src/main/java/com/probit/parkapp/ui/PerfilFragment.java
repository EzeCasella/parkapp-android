package com.probit.parkapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.probit.parkapp.MainActivity;
import com.probit.parkapp.R;
import com.probit.parkapp.model.User;
import com.probit.parkapp.repositories.AuthRepository;

public class PerfilFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public PerfilFragment() {

    }

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    int position;
    int posicion;

    String nombreperfil;
    String email;
    String telefono;
    String id;
    String vehicle;

    EditText tvNombrePerfil;
    TextView tvEmail;
    EditText etTelefono;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        spinner = root.findViewById(R.id.spinner_Vehiculo);
        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.Vehiculos, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        vehicle = spinner.getItemAtPosition(position).toString();

        tvNombrePerfil = (EditText) root.findViewById(R.id.nombreperfil);
        tvEmail        = (TextView) root.findViewById(R.id.textMail);
        etTelefono     = (EditText) root.findViewById(R.id.textContact);

        AuthRepository.getCurrentUser(data -> { User user = (User) data;

            setearDatos(user);

        }, data -> {});



       return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button_save = view.findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setName(tvNombrePerfil.getText().toString());
                user.setEmail(tvEmail.getText().toString());
                user.setPhone(etTelefono.getText().toString());
                user.setId(id);
                user.setVehicle(vehicle);

                AuthRepository.updateCreateUser(user, data -> {
                    Toast.makeText(getContext(), "Se actualizaron sus datos", Toast.LENGTH_SHORT).show();
                }, data ->{
                    Toast.makeText(getContext(), "Se produjo un error y no se pudo actualizar, intente nuevamente", Toast.LENGTH_SHORT).show();
                });


            }
        });

        Button button_logout = view.findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AuthRepository.signOut();
                ((MainActivity) requireActivity()).navigateToLoginActivity();
            }
        });

        ImageView imageView;
        imageView = view.findViewById(R.id.circleImageView);
        String url = "https://www.pngkey.com/png/full/73-730477_first-name-profile-image-placeholder-png.png";
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_not_supported_24)
                .circleCrop()
                .into(imageView);
    }

    public void setearDatos(User user){
        nombreperfil = user.getName();
        email        = user.getEmail();
        telefono     = user.getPhone();
        id           = user.getId();
        vehicle      = user.getVehicle();

        spinner.setSelection(adapter.getPosition(vehicle));

        tvNombrePerfil.setText(nombreperfil);
        tvEmail.setText(email);
        etTelefono.setText(telefono);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        vehicle = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        vehicle = parent.getItemAtPosition(0).toString();
    }


}
