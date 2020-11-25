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
import com.mikeescom.loginapp.SharedPreferenceUtils;
import com.mikeescom.loginapp.repository.db.User;
import com.mikeescom.loginapp.viewmodel.AppViewModel;

public class LoginFragment extends Fragment {

    private AppViewModel viewModel;
    private TextInputEditText userNameEditText;
    private TextInputEditText pswNameEditText;
    private Button logIn;
    private Button signUp;
    private SharedPreferenceUtils sp;

    private final Observer<User> findByUserIdAndPswObserver = user -> {
        if (user != null) {
            sp.setLoggedUserId(user.getId());
            goToProfile(user.getId());
        } else {
            userNameEditText.setText("");
            pswNameEditText.setText("");
            Toast.makeText(getContext(), getResources().getString(R.string.user_does_not_exist), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        sp = SharedPreferenceUtils.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        if (sp.getLoggedUserId() != 0) {
            goToProfile(sp.getLoggedUserId());
        }
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        userNameEditText = view.findViewById(R.id.user_name_edit_text);
        pswNameEditText = view.findViewById(R.id.password_edit_text);
        logIn = view.findViewById(R.id.log_in);
        logIn.setOnClickListener(v -> viewModel.validateLogin(userNameEditText.getText().toString()
                , pswNameEditText.getText().toString()));
        signUp = view.findViewById(R.id.sign_up);
        signUp.setOnClickListener(v -> goToRegister());

        observeLoginData();

    }

    private void observeLoginData() {
        viewModel.isLoginDataValid.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                findByUserIdAndPsw();
                userNameEditText.setError(null);
                pswNameEditText.setError(null);
            }
        });

        viewModel.isLoginUserIdValid.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                userNameEditText.setError(null);
            } else {
                userNameEditText.setError(getResources().getString(R.string.user_name_error_message));
            }
        });

        viewModel.isLoginPasswordValid.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                pswNameEditText.setError(null);
            } else {
                pswNameEditText.setError(getResources().getString(R.string.password_error_message));
            }
        });
    }

    private void findByUserIdAndPsw() {
        viewModel.findByUserIdAndPsw(userNameEditText.getText().toString()
                        , pswNameEditText.getText().toString()).observe(getViewLifecycleOwner(), findByUserIdAndPswObserver);
    }

    private void goToProfile(int id) {
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        intent.putExtra("USER_ID", id);
        startActivity(intent);
    }

    private void goToRegister() {
        NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_loginFragment_to_registerFragment);
    }
}