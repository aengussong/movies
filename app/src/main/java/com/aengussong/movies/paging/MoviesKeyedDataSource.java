package com.aengussong.movies.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.aengussong.movies.model.Movie;
import com.aengussong.movies.network.ApiClient;
import com.aengussong.movies.network.callback.NetworkCallback;
import com.aengussong.movies.network.errorResponse.NetworkError;
import com.aengussong.movies.network.response.MoviesResponse;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class MoviesKeyedDataSource extends PageKeyedDataSource<Integer, Movie> {

    private ApiClient apiClient;

    private CompositeDisposable compositeDisposable;

    private int page = 1;

    private MutableLiveData<NetworkState> networkState;
    private MutableLiveData<NetworkState> initialLoading;

    private Completable retryCompletable;

    public MoviesKeyedDataSource(CompositeDisposable compositeDisposable, ApiClient apiClient) {
        this.apiClient = apiClient;
        this.compositeDisposable = compositeDisposable;

        networkState = new MutableLiveData<>();
        initialLoading = new MutableLiveData<>();
    }

    public void retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                    }, throwable -> Log.e("DataSource", throwable.getMessage())));
        }
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public MutableLiveData<NetworkState> getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        compositeDisposable.add(apiClient.fetchMovies(page, new NetworkCallback<MoviesResponse>() {

            @Override
            public void onSuccess(MoviesResponse response) {
                setRetry(null);
                callback.onResult(response.getResults(), null, page + 1);
                initialLoading.postValue(NetworkState.LOADED);
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onError(NetworkError error) {
                setRetry(() -> loadInitial(params, callback));
                NetworkState networkStateError = NetworkState.error(error.getAppErrorMessage());
                networkState.postValue(networkStateError);
                initialLoading.postValue(networkStateError);
            }
        }));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        page++;
        networkState.postValue(NetworkState.LOADING);
        compositeDisposable.add(apiClient.fetchMovies(params.key, new NetworkCallback<MoviesResponse>() {
            @Override
            public void onSuccess(MoviesResponse response) {
                setRetry(null);
                callback.onResult(response.getResults(), page + 1);
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onError(NetworkError error) {
                setRetry(() -> loadAfter(params, callback));
                networkState.postValue(NetworkState.error(error.getAppErrorMessage()));
            }
        }));
    }

    private void setRetry(final Action action) {
        if (action == null) {
            this.retryCompletable = null;
        } else {
            this.retryCompletable = Completable.fromAction(action);
        }
    }
}