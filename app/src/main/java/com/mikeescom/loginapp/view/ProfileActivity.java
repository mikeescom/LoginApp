package com.mikeescom.loginapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mikeescom.loginapp.R;
import com.mikeescom.loginapp.SharedPreferenceUtils;
import com.mikeescom.loginapp.repository.db.User;
import com.mikeescom.loginapp.viewmodel.AppViewModel;

public class ProfileActivity extends AppCompatActivity {

    private AppViewModel viewModel;
    private SharedPreferenceUtils sp;
    private TextView fullName;
    private TextView email;
    private TextView userName;
    private Button logOut;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        sp = SharedPreferenceUtils.getInstance(this);
        userId = getIntent().getIntExtra("USER_ID", 0);
        viewModel.findByUserId(userId).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                updateUI(user);
            }
        });
    }

    private void initViews() {
        fullName = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        userName = findViewById(R.id.user_id);
        logOut = findViewById(R.id.log_out);
        logOut.setOnClickListener(v -> {
            sp.setLoggedUserId(0);
            finish();
        });
    }

    private void updateUI(User user) {
        if (user == null) {
            Toast.makeText(this, getResources().getString(R.string.user_info_could_not_be_loaded), Toast.LENGTH_LONG).show();
            return;
        }

        fullName.setText(user.getFirstName() + " " + user.getLastName());
        email.setText(user.getEmail());
        userName.setText(user.getUserId());
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, getResources().getString(R.string.press_log_out_button), Toast.LENGTH_LONG).show();
    }
}