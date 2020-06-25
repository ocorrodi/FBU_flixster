package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    TextView titleText;
    TextView overviewText;
    RatingBar rating;
    ImageView bgImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        titleText = findViewById(R.id.titleText);
        overviewText = findViewById(R.id.overviewText);
        rating = findViewById(R.id.rating);
        bgImage = findViewById(R.id.bgImage);

        //get the movie
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        titleText.setText(movie.getTitle());
        overviewText.setText(movie.getOverview());
        float voteAverage = movie.getVote_average().floatValue();
        Glide.with(this).load(movie.getBackdropPath()).into(bgImage);
        rating.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
    }
}