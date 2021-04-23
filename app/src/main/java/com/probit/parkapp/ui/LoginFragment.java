package com.probit.parkapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.probit.parkapp.LoginSignupActivity;
import com.probit.parkapp.R;
import com.probit.parkapp.repositories.AuthRepository;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";


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

                AuthRepository.login(userEmail.getText().toString(),userPass.getText().toString(),
                        _data -> ((LoginSignupActivity)requireActivity()).navigateToMainActivity(),
                        errorStringId -> Toast.makeText(requireActivity(), getString((Integer) errorStringId), Toast.LENGTH_SHORT).show()
                );

            }
        });
        
    }
}