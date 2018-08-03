package com.aengussong.movies.network;

import com.aengussong.movies.network.callback.NetworkCallback;
import com.aengussong.movies.network.errorResponse.NetworkError;
import com.aengussong.movies.network.response.MoviesResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ApiClient {

    private final ApiLinks requestsLinks;

    public ApiClient(ApiLinks links) {
        requestsLinks = links;
    }

    public Disposable fetchMovies(int page, NetworkCallback<MoviesResponse> callback) {
        return requestsLinks.getMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess, throwable -> callback.onError(new NetworkError(throwable)));
    }
}
