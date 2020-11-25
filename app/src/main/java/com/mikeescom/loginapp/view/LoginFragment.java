package com.mikeescom.loginapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import com.mikeescom.loginapp.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel viewModel;
    private TextInputEditText userNameEditText;
    private TextInputEditText pswNameEditText;
    private Button logIn;
    private Button signUp;
    private final Observer<Boolean> validateLoginDataObserver = aBoolean -> {
        if (aBoolean) {
            findByUserIdAndPsw();
            userNameEditText.setError(null);
            pswNameEditText.setError(null);
        } else {
            userNameEditText.setError(getResources().getString(R.string.user_name_error_message));
            pswNameEditText.setError(getResources().getString(R.string.password_error_message));
        }
    };
    private final Observer<User> findByUserIdAndPswObserver = user -> {
        if (user != null) {
            goToProfile();
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.user_does_not_exist), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        userNameEditText = view.findViewById(R.id.user_name_edit_text);
        pswNameEditText = view.findViewById(R.id.password_edit_text);
        logIn = view.findViewById(R.id.log_in);
        logIn.setOnClickListener(v -> viewModel.validateLogin(userNameEditText.getText().toString()
                , pswNameEditText.getText().toString()).observe(getViewLifecycleOwner(), validateLoginDataObserver));
        signUp = view.findViewById(R.id.sign_up);
        signUp.setOnClickListener(v -> goToRegister());
    }

    private void findByUserIdAndPsw() {
        viewModel.findByUserIdAndPsw(userNameEditText.getText().toString()
                        , pswNameEditText.getText().toString()).observe(getViewLifecycleOwner(), findByUserIdAndPswObserver);
    }

    private void goToProfile() {
        Intent intent = new Intent();
    }

    private void goToRegister() {
        NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_loginFragment_to_registerFragment);
    }
}