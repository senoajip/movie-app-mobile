package com.example.tesprojek.view.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tesprojek.model.User;
import com.example.tesprojek.service.AppRepository;

import org.jetbrains.annotations.NotNull;

public class AuthViewModel extends AndroidViewModel {
    protected AppRepository repository;

    public AuthViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository = new AppRepository(application);
    }

    public void register(User user){
        repository.register(user);
    }
    public LiveData<User> login(String username, String password){
        return repository.login(username, password);
    }
}
