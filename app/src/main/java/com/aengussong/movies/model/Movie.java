package com.aengussong.movies.model;

import android.support.v7.util.DiffUtil;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {

    private String id;

    @SerializedName("vote_count")
    private String voteCount;
    @SerializedName("vote_average")
    private String voteAverage;

    private String title;
    @SerializedName("poster_path")
    private String posterPath;

    public Movie(String id, String voteCount, String voteAverage, String title, String posterPath) {
        this.id = id;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
    }

    public String getId() {
        return id;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(Movie oldItem, Movie newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(Movie oldItem, Movie newItem) {
            if (!oldItem.voteCount.equals(newItem.voteCount))
                return false;
            if (!oldItem.voteAverage.equals(newItem.voteAverage))
                return false;
            if (!oldItem.title.equals(newItem.title))
                return false;
            if (!oldItem.posterPath.equals(newItem.posterPath))
                return false;
            return true;
        }
    };
}
