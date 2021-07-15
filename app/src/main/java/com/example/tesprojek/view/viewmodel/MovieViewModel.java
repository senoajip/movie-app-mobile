package com.example.tesprojek.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tesprojek.model.movie.MovieResponse;
import com.example.tesprojek.model.movie.MovieResultsItem;
import com.example.tesprojek.service.ApiMain;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    private ApiMain apiMain;

    private MutableLiveData<ArrayList<MovieResultsItem>> listMovie = new MutableLiveData<>();

    public void setMovie(){
        if (this.apiMain == null){
            apiMain = new ApiMain();
        }
        apiMain.getApiMovie().getMovie().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse responseDsc = response.body();
                if (responseDsc != null && responseDsc.getResults() != null){
                    ArrayList<MovieResultsItem> movieItems = responseDsc.getResults();
                    listMovie.postValue(movieItems);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<ArrayList<MovieResultsItem>> getMovie(){
        return listMovie;
    }
}
