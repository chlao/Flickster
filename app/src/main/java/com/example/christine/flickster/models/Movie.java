package com.example.christine.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Christine on 7/14/2016.
 */
public class Movie {
    String posterPath;
    String originalTitle;
    String overview;
    String backdrop;
    double rating;

    MovieType movieType;

    public MovieType getMovieType() {
        return movieType;
    }

    public double getRating() {
        return rating;
    }

    public String getBackdrop() {
        return String.format("https://image.tmdb.org/t/p/w1280/%s", backdrop);
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w500/%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public enum MovieType{
        POPULAR, STANDARD
    }

    // throws JSONException: will bubble up to caller trying to instantiate
    public Movie (JSONObject object) throws JSONException{
        this.posterPath = object.getString("poster_path");
        this.originalTitle = object.getString("original_title");
        this.overview = object.getString("overview");
        this.backdrop = object.getString("backdrop_path");
        this.rating = Double.parseDouble(object.getString("vote_average"));

        if (this.rating >= 5){
            this.movieType = movieType.values()[0];
        } else {
            this.movieType = movieType.values()[1];
        }
    }

    /** static: only one instance of a static field exists (shared by all instances of class)
     *  General rule of thumb: Does it make sense to call this method even
     *                         if no object has been constructed yet?
     */
    public static ArrayList<Movie> fromJSONArray(JSONArray array){
        ArrayList<Movie> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++){
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

}
