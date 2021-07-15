package com.example.tesprojek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tesprojek.model.User;
import com.example.tesprojek.view.viewmodel.AuthViewModel;

import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterActivity extends AppCompatActivity {
    protected EditText register_name, register_username, register_password;
    protected AuthViewModel authViewModel;
    protected Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        initUI();

        btn_register.setOnClickListener(v -> {
            checkRegister();
        });

    }

    private void initUI() {
        register_name          = findViewById(R.id.register_name);
        register_username      = findViewById(R.id.register_username);
        register_password      = findViewById(R.id.register_password);
        btn_register           = findViewById(R.id.btn_register);
        authViewModel          = new ViewModelProvider.AndroidViewModelFactory(RegisterActivity.this.getApplication()).create(AuthViewModel.class);
    }

    private void checkRegister() {
        register_username.setError(null);
        register_password.setError(null);
        View focused = null;
        AtomicBoolean cancel = new AtomicBoolean(false);

        String name      = register_name.getText().toString().trim();
        String username  = register_username.getText().toString().trim();
        String password  = register_password.getText().toString().trim();

        if(name.isEmpty()) {
            register_name.setError("This field is required");
            focused = register_name;
            cancel.set(true);
        }

        if(username.isEmpty()) {
            register_username.setError("This field is required");
            focused = register_username;
            cancel.set(true);
        }

        if(password.isEmpty()) {
            register_password.setError("This field is required");
            focused = register_password;
            cancel.set(true);
        }

        if(cancel.get()) {
            assert focused != null;
            focused.requestFocus();
        } else {

            User user = new User();
            user.setName(name);
            user.setUsername(username);
            user.setPassword(password);

            Log.d("ghghg","name:"+name+"\nusername:"+username+"\npassword:"+password);

            authViewModel.register(user);
            finish();
            Toast.makeText(getBaseContext(), "Successful", Toast.LENGTH_SHORT).show();
        }
    }
}