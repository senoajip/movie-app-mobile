package com.example.tesprojek.adapter;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.tesprojek.DetailMovieActivity;
import com.example.tesprojek.R;
import com.example.tesprojek.model.movie.MovieResultsItem;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ArrayList<MovieResultsItem> movieItems = new ArrayList<>();

    private Context context;

    private static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/";

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<MovieResultsItem> items){
        movieItems.clear();
        movieItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);

        MovieAdapter.ViewHolder viewHolder = new MovieAdapter.ViewHolder(view);
        viewHolder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), DetailMovieActivity.class);
                MovieResultsItem movieResultsItem = new MovieResultsItem();
                movieResultsItem.setOriginalTitle(movieItems.get(viewHolder.getAdapterPosition()).getOriginalTitle());
                movieResultsItem.setOverview(movieItems.get(viewHolder.getAdapterPosition()).getOverview());
                movieResultsItem.setPosterPath(movieItems.get(viewHolder.getAdapterPosition()).getPosterPath());
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieResultsItem);
                parent.getContext().startActivity(intent);
            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MovieAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(BASE_IMAGE_URL+movieItems.get(position).getPosterPath())
                .into(holder.ivThumb);

        holder.tvTitle.setText(movieItems.get(position).getTitle());
        holder.tvRate.setText(String.valueOf(movieItems.get(position).getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivThumb;
        TextView tvTitle,tvRate;
        CardView cvItem;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            cvItem = itemView.findViewById(R.id.itemlist_cv);
            ivThumb = itemView.findViewById(R.id.itemlist_iv_thumbnail);
            tvTitle = itemView.findViewById(R.id.itemlist_tv_title);
            tvRate = itemView.findViewById(R.id.itemlist_tv_rate);


        }
    }
}
