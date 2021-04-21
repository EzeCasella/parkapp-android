package com.probit.parkapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.probit.parkapp.LoginSignupActivity;
import com.probit.parkapp.MainActivity;
import com.probit.parkapp.R;
import com.probit.parkapp.common.Callback;
import com.probit.parkapp.model.User;
import com.probit.parkapp.repositories.AuthRepository;

public class PerfilFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public PerfilFragment() {

    }

    Spinner spinner;

    String nombreperfil;
    String email;
    String telefono;
    String id;

    TextView tvNombrePerfil;
    TextView tvEmail;
    EditText etTelefono;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        spinner = root.findViewById(R.id.spinner_Vehiculo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.Vehiculos, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        tvNombrePerfil = (TextView) root.findViewById(R.id.nombreperfil);
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
                user.setName((String) tvNombrePerfil.getText());
                user.setEmail((String) tvEmail.getText());
                user.setPhone(etTelefono.getText().toString());
                user.setId(id);

//                spinner.getSelectedItem();

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
    }

    public void setearDatos(User user){
        nombreperfil = user.getName();
        email        = user.getEmail();
        telefono     = user.getPhone();
        id           = user.getId();


        tvNombrePerfil.setText(nombreperfil);
        tvEmail.setText(email);
        etTelefono.setText(telefono);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(), "Usted a seleccionado: "+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
