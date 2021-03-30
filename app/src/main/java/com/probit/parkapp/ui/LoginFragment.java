package com.probit.parkapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.probit.parkapp.LoginSignupActivity;
import com.probit.parkapp.R;

public class LoginFragment extends Fragment {

    EditText userEmail;
    EditText userPass;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        userEmail = (EditText) view.findViewById(R.id.login_user_email);
        userPass = (EditText) view.findViewById(R.id.login_user_password);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_LoginFragment_to_SignupFragment);
            }
        });

        Button signinButton = view.findViewById(R.id.login_button);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Deberia validar los campos antes de mandar, y despues mandar

                ((LoginSignupActivity)getActivity()).login(userEmail.getText().toString(),userPass.getText().toString());

            }
        });

        Button signOutButton = view.findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginSignupActivity)getActivity()).signOut();
            }
        });
    }
}