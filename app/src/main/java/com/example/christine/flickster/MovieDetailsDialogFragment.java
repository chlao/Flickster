package com.example.christine.flickster;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.christine.flickster.models.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by Christine on 7/19/2016.
 */
public class MovieDetailsDialogFragment extends DialogFragment {
    private ImageView posterImage;
    private TextView movieSynopsis;
    private RatingBar movieRating;

    public MovieDetailsDialogFragment() {
    }

    /*
     *  Android relies on Fragments having a public, zero-argument constructor
     *  so that it can recreate it at various times (e.g. configuration changes,
     *  restoring the app state after being previously killed by Android, etc.).
     */
    public static MovieDetailsDialogFragment newInstance(String title, String posterImage, String synopsis, float rating){
        MovieDetailsDialogFragment fragment = new MovieDetailsDialogFragment();
        Bundle args = new Bundle();

        args.putString("movieTitle", title);
        args.putString("moviePoster", posterImage);
        args.putString("movieSynopsis", synopsis);
        args.putFloat("movieRating", rating);

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieRating = (RatingBar) view.findViewById(R.id.movieRating);
        movieSynopsis = (TextView) view.findViewById(R.id.movieSynopsis);
        posterImage = (ImageView) view.findViewById(R.id.posterImage);

        Bundle args = getArguments();

        String title = args.getString("movieTitle");
        String imageURL = args.getString("moviePoster");
        String synopsis = args.getString("movieSynopsis");
        Float rating = args.getFloat("movieRating");

        getDialog().setTitle(title);

        movieRating.setRating(rating);
        movieSynopsis.setText(synopsis);

        posterImage.setImageResource(0);
        Picasso.with(view.getContext()).load(imageURL)
                .placeholder(R.drawable.movie_placeholder)
                .into(posterImage);
    }
}
