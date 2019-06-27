package com.codepath.debuggingchallenges.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.debuggingchallenges.R;
import com.codepath.debuggingchallenges.models.Movie;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private ArrayList<Movie> movies;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // only needed because we need to set the background color
        View view;

        // Lookup view for data population
        TextView tvName;
        TextView tvRating;
        ImageView ivPoster;

        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tvTitle);
            tvRating =(TextView) itemView.findViewById(R.id.tvRating);
            ivPoster = (ImageView) itemView.findViewById(R.id.ivPoster);
        }
    }

    public MoviesAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);

        // Return a new holder instance
        return new ViewHolder(movieView);
    }


    @Override
    public void onBindViewHolder(@Nullable ViewHolder viewHolder, int position) {

        Movie movie = movies.get(position);

        // Populate the data into the template view using the data object
        viewHolder.tvName.setText(movie.getTitle());

        Resources resources = viewHolder.tvName.getResources();
        double movieRating = movie.getRating();

        if (movieRating > 6) {
            viewHolder.view.setBackgroundColor(Color.GREEN);
        }

        String ratingText = String.format(resources.getString(R.string.rating), movieRating);
        viewHolder.tvRating.setText(ratingText);

        Glide.with(viewHolder.ivPoster.getContext()).load(movie.getPosterUrl()).into(
                viewHolder.ivPoster);

    }



}
