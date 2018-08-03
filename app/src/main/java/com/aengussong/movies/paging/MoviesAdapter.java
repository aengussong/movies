package com.aengussong.movies.paging;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aengussong.movies.R;
import com.aengussong.movies.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends PagedListAdapter<Movie, RecyclerView.ViewHolder> {

    private NetworkState networkState;
    private RetryCallback retryCallback;

    public MoviesAdapter(RetryCallback retryCallback) {
        super(Movie.DIFF_CALLBACK);
        this.retryCallback = retryCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.item_movie:
                return MovieViewHolder.create(parent);
            case R.layout.item_network_state:
                return NetworkStateItemViewHolder.create(parent, retryCallback);
            default:
                throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.item_movie:
                ((MovieViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.item_network_state:
                ((NetworkStateItemViewHolder) holder).bindTo(networkState);
                break;
        }
    }

    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.item_network_state;
        } else {
            return R.layout.item_movie;
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasExtraRow() ? 1 : 0);
    }


    public void setNetworkState(NetworkState newNetworkState) {
        if (getCurrentList() != null) {
            if (getCurrentList().size() != 0) {
                NetworkState previousState = this.networkState;
                boolean hadExtraRow = hasExtraRow();
                this.networkState = newNetworkState;
                boolean hasExtraRow = hasExtraRow();
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount());
                    } else {
                        notifyItemInserted(super.getItemCount());
                    }
                } else if (hasExtraRow && previousState != newNetworkState) {
                    notifyItemChanged(getItemCount() - 1);
                }
            }
        }
    }


    static class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.errorMessageTextView)
        TextView errorMessageTextView;

        @BindView(R.id.retryLoadingButton)
        Button retryLoadingButton;

        @BindView(R.id.loadingProgressBar)
        ProgressBar loadingProgressBar;

        private NetworkStateItemViewHolder(View itemView, RetryCallback retryCallback) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            retryLoadingButton.setOnClickListener(v -> retryCallback.retry());
        }

        public void bindTo(NetworkState networkState) {
            errorMessageTextView.setVisibility(networkState.getMessage() != null ? View.VISIBLE : View.GONE);
            if (networkState.getMessage() != null) {
                errorMessageTextView.setText(networkState.getMessage());
            }

            retryLoadingButton.setVisibility(networkState.getStatus() == Status.FAILED ? View.VISIBLE : View.GONE);
            loadingProgressBar.setVisibility(networkState.getStatus() == Status.RUNNING ? View.VISIBLE : View.GONE);
        }

        public static NetworkStateItemViewHolder create(ViewGroup parent, RetryCallback retryCallback) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.item_network_state, parent, false);
            return new NetworkStateItemViewHolder(view, retryCallback);
        }
    }
}
