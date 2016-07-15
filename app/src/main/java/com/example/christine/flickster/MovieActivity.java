package com.example.christine.flickster;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.ListView;

import com.example.christine.flickster.adapters.MovieArrayAdapter;
import com.example.christine.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;

    ListView lvItems;

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvItems = (ListView) findViewById(R.id.lvViews);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchMovies();
            }
        });

        fetchMovies();
    }

    public void fetchMovies(){
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        AsyncHttpClient client = new AsyncHttpClient();

        // get( , responseHandler)
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieResults = null;

                try {
                    movieResults = response.getJSONArray("results");

                    /**
                     *  movies = Movie.fromJSONArray(movieResults);
                     *  Changing reference of movie to be new block of data, don't track old movies
                     *  Adapter is still tracking the old movies
                     */
                    movies.addAll(Movie.fromJSONArray(movieResults));
                    movieAdapter.notifyDataSetChanged();

                    // Now we call setRefreshing(false) to signal refresh has finished
                    swipeContainer.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
