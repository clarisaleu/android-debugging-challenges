package com.codepath.debuggingchallenges.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.codepath.debuggingchallenges.R;
import com.codepath.debuggingchallenges.adapters.MoviesAdapter;
import com.codepath.debuggingchallenges.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MoviesActivity extends AppCompatActivity {

    private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    RecyclerView rvMovies;
    MoviesAdapter adapter;
    ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        rvMovies = (RecyclerView)findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        movies = new ArrayList<>();

        // Create the adapter to convert the array to views
        adapter = new MoviesAdapter(movies);


        // Attach the adapter to a ListView
        rvMovies.setAdapter(adapter);
        fetchMovies();
    }


    private void fetchMovies() {
        String url = " https://api.themoviedb.org/3/movie/now_playing?api_key=";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api_key", API_KEY);  // API key, always required
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray moviesJson = response.getJSONArray("results");
                    for(int i = 0; i < moviesJson.length(); i++){
                        Movie movie = new Movie(moviesJson.getJSONObject(i));
                        movies.add(movie);
                        adapter.notifyItemInserted(movies.size()-1);
                    }
                    // i tag for information
                    Log.i("movies", String.format("Loaded %s movies", moviesJson.length()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get data from fetchMovies endpoint", throwable, true);
            }
        });
    }
    // handle errors, log and alert user
    private void logError(String message, Throwable error, boolean alertUser){
        // always log the error
        Log.e("movies", message, error);
        // alert the user to avoid silent errors
        if(alertUser){
            // show a long toast with the error message
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
