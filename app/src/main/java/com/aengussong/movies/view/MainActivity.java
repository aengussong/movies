package com.aengussong.movies.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aengussong.movies.R;
import com.aengussong.movies.paging.MoviesAdapter;
import com.aengussong.movies.paging.NetworkState;
import com.aengussong.movies.paging.RetryCallback;
import com.aengussong.movies.paging.Status;
import com.aengussong.movies.viewModel.MainActivityViewModel;
import com.aengussong.movies.viewModel.MoviesViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements RetryCallback {

    @Inject
    MoviesViewModelFactory viewModelFactory;

    @BindView(R.id.moviesRecyclerView)
    RecyclerView moviesRecyclerView;

    @BindView(R.id.errorMessageTextView)
    TextView errorMessageTextView;

    @BindView(R.id.retryLoadingButton)
    Button retryLoadingButton;

    @BindView(R.id.loadingProgressBar)
    ProgressBar loadingProgressBar;

    private MainActivityViewModel viewModel;

    private MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);

        initAdapter();
        init();

    }

    @OnClick(R.id.retryLoadingButton)
    void retryInitialLoading() {
        viewModel.retry();
    }

    @Override
    public void retry() {
        viewModel.retry();
    }

    private void initAdapter() {
        int columns = this.getResources().getInteger(R.integer.columns);
        GridLayoutManager layoutManager = new GridLayoutManager(this, columns);
        adapter = new MoviesAdapter(this);
        moviesRecyclerView.setLayoutManager(layoutManager);
        moviesRecyclerView.setAdapter(adapter);

        viewModel.pagedListLiveData.observe(this, adapter::submitList);
        viewModel.getNetworkState().observe(this, adapter::setNetworkState);
    }

    private void init() {
        viewModel.getRefreshState().observe(this, networkState -> {
            if (networkState != null) {
                setInitialLoadingState(networkState);
            }
        });
    }

    private void setInitialLoadingState(NetworkState networkState) {
        errorMessageTextView.setVisibility(networkState.getMessage() != null ? View.VISIBLE : View.GONE);
        if (networkState.getMessage() != null) {
            errorMessageTextView.setText(networkState.getMessage());
        }

        retryLoadingButton.setVisibility(networkState.getStatus() == Status.FAILED ? View.VISIBLE : View.GONE);
        loadingProgressBar.setVisibility(networkState.getStatus() == Status.RUNNING ? View.VISIBLE : View.GONE);
    }
}
