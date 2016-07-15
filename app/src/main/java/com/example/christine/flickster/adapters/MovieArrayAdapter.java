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
    int ORIENTATION_PORTRAIT = 1;
    int ORIENTATION_LANDSCAPE = 2;
    /** View lookup cache - once ListView has reach max amount of rows
     *  it can display on a screen, Android begins recycling views
     */
    private static class ViewHolder {
        TextView tvTitle;
        TextView tvOVerview;
        ImageView ivImage;
        ImageView backdrop;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies){
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Movie movie = getItem(position);

        /**
         *  Speedholder pattern: speeds up the population of the ListView
         *  considerably by caching view lookups for smoother, faster item loading
         */
        final ViewHolder viewHolder; // view lookup cache stored in tag
        // Check the existing view being reused
        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);

            // Making calls to findViewById() is really slow in practice
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOVerview = (TextView) convertView.findViewById(R.id.tvOverview);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.backdrop = (ImageView) convertView.findViewById(R.id.ivMovieImageLand);

            convertView.setTag(viewHolder); // attach an object onto a View
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

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

        return convertView;
    }
}
