package com.example.tesprojek.service;

import android.app.Application;
import android.provider.SyncStateContract;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tesprojek.dao.UserDao;
import com.example.tesprojek.model.User;
import com.example.tesprojek.model.movie.MovieResponse;
import com.example.tesprojek.model.movie.MovieResultsItem;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {
    private final ApiMain apiMain = new ApiMain();
    private  final UserDao userDao;

    public AppRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDao = db.userDao();
    }

    // -- API
    public LiveData<ArrayList<MovieResultsItem>> getMovie() {
        final MutableLiveData<ArrayList<MovieResultsItem>> movie = new MutableLiveData<>();

        Call<MovieResponse> getResponse = apiMain.getApiMovie().getMovie();
        getResponse.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if (movieResponse != null && movieResponse.getResults() != null) {
                    ArrayList<MovieResultsItem> movieResultsItems = movieResponse.getResults();
                    movie.postValue(movieResultsItems);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
        return movie;
    }

    // -- Database
    public void register(User user){
        AppDatabase.databaseWriterExecutor.execute(()-> userDao.register(user));
    }

    public LiveData<User> login(String username, String password){
        return userDao.login(username, password);
    }
}
