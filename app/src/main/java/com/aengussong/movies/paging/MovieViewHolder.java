package com.aengussong.movies.paging;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aengussong.movies.R;
import com.aengussong.movies.model.Movie;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aengussong.movies.data.Constants.BASE_IMAGE_URL;

class MovieViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.vote_average)
    TextView voteAverage;
    @BindView(R.id.vote_count)
    TextView voteCount;
    @BindView(R.id.poster)
    ImageView poster;

    private MovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindTo(Movie movie) {

        title.setText(movie.getTitle());
        voteAverage.setText(itemView.getContext().getString(R.string.vote_average, movie.getVoteAverage()));
        voteCount.setText(itemView.getContext().getString(R.string.vote_count, movie.getVoteCount()));

        Glide.with(itemView.getContext())
                .load(BASE_IMAGE_URL + movie.getPosterPath())
                .into(poster);
    }

    public static MovieViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }
}
