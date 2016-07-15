package com.example.christine.flickster.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.christine.flickster.models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christine on 7/14/2016.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie>{
    public MovieArrayAdapter(Context context, List<Movie> movies){
        super(context, android.R.layout.simple_list_item_1, movies);
    }
}
