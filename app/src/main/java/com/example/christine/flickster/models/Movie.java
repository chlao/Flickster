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

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    // throws JSONException: will bubble up to caller trying to instantiate
    public Movie (JSONObject object) throws JSONException{
        this.posterPath = object.getString("poster_path");
        this.originalTitle = object.getString("original_title");
        this.overview = object.getString("overview");
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
