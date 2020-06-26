package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    String URL;
    String videoId;
    public static final String YouTube_API_KEY = "AIzaSyAOVR1TlQI12Vx0ahmL_X4VVuqaEtJFtew";
    public static final String MovieDB_API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        URL = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key=" + MovieDB_API_KEY + "&language=en-US";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject videoObj = results.getJSONObject(0);
                    videoId = videoObj.getString("key");
                } catch (JSONException e) {
                    Log.e("MovieTrailerActivity", "json exception", e);
                    e.printStackTrace();
                }
                YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);
                playerView.initialize(YouTube_API_KEY, new YouTubePlayer.OnInitializedListener() {

                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        Log.e("MovieTrailerActivity", "Error with YouTube video player");
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("MovieTrailerActivity", "failed to play video");
            }
        });

    }
}