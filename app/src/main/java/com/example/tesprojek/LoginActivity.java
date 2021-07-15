package com.example.tesprojek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tesprojek.view.activity.MainActivity;
import com.example.tesprojek.view.viewmodel.AuthViewModel;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginActivity extends AppCompatActivity {
    protected TextView register;
    protected Button btn_login;
    protected EditText tv_username, tv_password;
    protected AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        authViewModel = new ViewModelProvider.AndroidViewModelFactory(LoginActivity.this.getApplication()).create(AuthViewModel.class);

        tv_username     = findViewById(R.id.username);
        tv_password     = findViewById(R.id.password);
        btn_login       = findViewById(R.id.login);
        register        = findViewById(R.id.register);

        tv_password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                loginCheck();
                return true;
            }
            return false;
        });

        register.setOnClickListener(v -> startActivity(new Intent(getBaseContext(),RegisterActivity.class)));
        btn_login.setOnClickListener(v -> {
            loginCheck();
        });

    }

    private void loginCheck() {
        tv_username.setError(null);
        tv_password.setError(null);
        View focused = null;
        AtomicBoolean cancel = new AtomicBoolean(false);

        String username = tv_username.getText().toString();
        String password = tv_password.getText().toString();

        if (TextUtils.isEmpty(username)){
            tv_username.setError("This field is required");
            focused = tv_username;
            cancel.set(true);
        }

        if (TextUtils.isEmpty(password)){
            tv_password.setError("This field is required");
            focused = tv_username;
            cancel.set(true);
        }

        if(cancel.get()) {
            assert focused != null;
            focused.requestFocus();
        }
        else {
            authViewModel.login(username, password).observe(this, user -> {
                if(user == null) {
                    tv_password.setError("Invalid Password");
                    tv_password.requestFocus();
                    cancel.set(true);
                }
                if(!cancel.get()) {
                    Intent intent = new Intent(this, MainActivity.class);
                    Toast.makeText(this, "Welcome back "+user.getName(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });
        }
    }
}