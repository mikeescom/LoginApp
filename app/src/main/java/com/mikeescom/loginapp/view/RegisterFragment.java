package com.mikeescom.loginapp.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mikeescom.loginapp.R;
import com.mikeescom.loginapp.repository.db.User;
import com.mikeescom.loginapp.viewmodel.AppViewModel;

public class RegisterFragment extends Fragment {
    private AppViewModel viewModel;
    private TextInputEditText firstNameEditText;
    private TextInputEditText lastNameEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText userNameEditText;
    private TextInputEditText passwordEditText;
    private Button register;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        firstNameEditText = view.findViewById(R.id.first_name_edit_text);
        lastNameEditText = view.findViewById(R.id.last_name_edit_text);
        emailEditText = view.findViewById(R.id.email_edit_text);
        userNameEditText = view.findViewById(R.id.user_name_edit_text);
        passwordEditText = view.findViewById(R.id.password_edit_text);
        register = view.findViewById(R.id.register);
        register.setOnClickListener(v -> {
            user = new User();
            user.setFirstName(firstNameEditText.getText().toString());
            user.setLastName(lastNameEditText.getText().toString());
            user.setEmail(emailEditText.getText().toString());
            user.setUserId(userNameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());
            viewModel.validateRegister(user);

            observeRegisterData();
        });
    }

    private void observeRegisterData() {
        viewModel.isRegisterDataValid.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                registerUser(user);
                hideErrorMessages();
            }
        });

        viewModel.isRegisterFirstNameValid.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                firstNameEditText.setError(null);
            } else {
                firstNameEditText.setError(getResources().getString(R.string.first_name_error_message));
            }
        });

        viewModel.isRegisterLastNameValid.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                lastNameEditText.setError(null);
            } else {
                lastNameEditText.setError(getResources().getString(R.string.last_name_error_message));
            }
        });

        viewModel.isRegisterEmailValid.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                emailEditText.setError(null);
            } else {
                emailEditText.setError(getResources().getString(R.string.email_error_message));
            }
        });

        viewModel.isRegisterUserIdValid.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                userNameEditText.setError(null);
            } else {
                userNameEditText.setError(getResources().getString(R.string.user_name_error_message));
            }
        });

        viewModel.isRegisterPasswordValid.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                passwordEditText.setError(null);
            } else {
                passwordEditText.setError(getResources().getString(R.string.password_error_message));
            }
        });
    }

    private void registerUser(User user) {
        viewModel.addUser(user).observe(getViewLifecycleOwner(), aLong -> {
            if (aLong != 0) {
                Toast.makeText(getContext(), getResources().getString(R.string.user_registered_successfully), Toast.LENGTH_LONG).show();
                goToLogin();
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.user_could_not_be_added), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goToLogin() {
        NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_registerFragment_to_loginFragment);
    }

    private void hideErrorMessages() {
        firstNameEditText.setError(null);
        lastNameEditText.setError(null);
        emailEditText.setError(null);
        userNameEditText.setError(null);
        passwordEditText.setError(null);
    }
}