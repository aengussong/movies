package com.aengussong.movies.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.aengussong.movies.model.Movie;
import com.aengussong.movies.network.ApiClient;

import io.reactivex.disposables.CompositeDisposable;

public class MoviesKeyedDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    private CompositeDisposable compositeDisposable;
    MutableLiveData<MoviesKeyedDataSource> dataSourceLiveData;

    private ApiClient apiClient;

    public MoviesKeyedDataSourceFactory(CompositeDisposable compositeDisposable, ApiClient apiClient) {
        this.compositeDisposable = compositeDisposable;
        this.apiClient = apiClient;
        this.dataSourceLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Movie> create() {
        MoviesKeyedDataSource dataSource = new MoviesKeyedDataSource(compositeDisposable, apiClient);
        dataSourceLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<MoviesKeyedDataSource> getDataSourceLiveData() {
        return dataSourceLiveData;
    }
}
