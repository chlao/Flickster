package com.example.christine.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.christine.flickster.R;
import com.example.christine.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christine on 7/14/2016.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie>{
    final int ORIENTATION_PORTRAIT = 1;
    final int ORIENTATION_LANDSCAPE = 2;
    /** View lookup cache - once ListView has reach max amount of rows
     *  it can display on a screen, Android begins recycling views
     */
    private static class ViewHolder {
        TextView tvTitle;
        TextView tvOVerview;
        ImageView ivImage;
        ImageView backdrop;
        ImageView backdropPopular;
        TextView tvTitlePopular;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies){
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Movie movie = getItem(position);

        /**
         *  Speedholder pattern: speeds up the population of the ListView
         *  considerably by caching view lookups for smoother, faster item loading
         */
        final ViewHolder viewHolder; // view lookup cache stored in tag

        // Get the data item type for this position
        int type = getItemViewType(position);

        // Check the existing view being reused
        if (convertView == null) {
            // Inflate XML layout based on the type
            convertView = getInflatedLayoutForType(type);

            viewHolder = getViewHolderForType(convertView, type);

            convertView.setTag(viewHolder); // attach an object onto a View
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        renderViewForType(type, viewHolder, movie);

        return convertView;
    }

    private View getInflatedLayoutForType(int type) {
        if (type == Movie.MovieType.POPULAR.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_popular, null);
        } else if (type == Movie.MovieType.STANDARD.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
        } else {
            return null;
        }
    }

    private ViewHolder getViewHolderForType(View convertView, int type){
        ViewHolder viewHolder = new ViewHolder();
        if (type == Movie.MovieType.POPULAR.ordinal()) {
            viewHolder.tvTitlePopular = (TextView) convertView.findViewById(R.id.tvTitlePopular);
            viewHolder.backdropPopular = (ImageView) convertView.findViewById(R.id.backdropPopular);
            return viewHolder;
        } else if (type == Movie.MovieType.STANDARD.ordinal()) {
            // Making calls to findViewById() is really slow in practice
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOVerview = (TextView) convertView.findViewById(R.id.tvOverview);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.backdrop = (ImageView) convertView.findViewById(R.id.ivMovieImageLand);
            return viewHolder;
        } else {
            return null;
        }
    }

    private void renderViewForType(int type, ViewHolder viewHolder, Movie movie){
        if (type == Movie.MovieType.POPULAR.ordinal()){
            viewHolder.tvTitlePopular.setText(movie.getOriginalTitle());

            viewHolder.backdropPopular.setImageResource(0);
            Picasso.with(getContext()).load(movie.getBackdrop())
                    .placeholder(R.drawable.movie_placeholder_land)
                    .into(viewHolder.backdropPopular);
        } else {
            Configuration config = getContext().getResources().getConfiguration();

            if (config.orientation == ORIENTATION_LANDSCAPE){
                // Clear out image from last time (in case view is reused)
                viewHolder.backdrop.setImageResource(0);
                Picasso.with(getContext()).load(movie.getBackdrop())
                        .placeholder(R.drawable.movie_placeholder_land)
                        .into(viewHolder.backdrop);
            } else{
                viewHolder.ivImage.setImageResource(0);
                Picasso.with(getContext()).load(movie.getPosterPath())
                        .placeholder(R.drawable.movie_placeholder)
                        .into(viewHolder.ivImage);
            }

            viewHolder.tvTitle.setText(movie.getOriginalTitle());
            viewHolder.tvOVerview.setText(movie.getOverview());
        }
    }

    // Returns the number of types of Views that will be created by getView(int, View, ViewGroup)
    @Override
    public int getViewTypeCount(){
        // Returns the number of types of Views that will be created by this adapter
        // Each type represents a set of views that can be converted
        return Movie.MovieType.values().length;
    }

    // Get the type of View that will be created by getView(int, View, ViewGroup)
    // for the specified item.
    @Override
    public int getItemViewType(int position) {
        // Return an integer here representing the type of View.
        // Note: Integers must be in the range 0 to getViewTypeCount() - 1
        return getItem(position).getMovieType().ordinal();
    }
}
