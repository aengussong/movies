package com.aengussong.movies.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.aengussong.movies.network.errorResponse.NetworkError;
import com.aengussong.movies.network.response.MoviesResponse;
import com.aengussong.movies.network.response.ResponseResult;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ApiClient {

    private final ApiLinks requestsLinks;

    private CompositeDisposable compositeDisposable;

    public ApiClient(ApiLinks links) {
        requestsLinks = links;
        compositeDisposable = new CompositeDisposable();
    }

    public LiveData<ResponseResult<MoviesResponse>> fetchMovies(int page) {
        final MutableLiveData<ResponseResult<MoviesResponse>> movies = new MutableLiveData<>();
        Disposable disposable = requestsLinks.getMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moviesResponse -> movies.setValue(new ResponseResult<>(moviesResponse)), throwable -> movies.setValue(new ResponseResult<MoviesResponse>(new NetworkError(throwable).getAppErrorMessage())));
        compositeDisposable.add(disposable);

        return movies;
    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
