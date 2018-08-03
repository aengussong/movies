package com.aengussong.movies.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.aengussong.movies.model.Movie;
import com.aengussong.movies.network.ApiClient;
import com.aengussong.movies.paging.MoviesKeyedDataSource;
import com.aengussong.movies.paging.MoviesKeyedDataSourceFactory;
import com.aengussong.movies.paging.NetworkState;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {

    public LiveData<PagedList<Movie>> pagedListLiveData;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MoviesKeyedDataSourceFactory factory;

    @Inject
    public MainActivityViewModel(ApiClient apiClient) {

        factory = new MoviesKeyedDataSourceFactory(compositeDisposable, apiClient);
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(2)
                .setPageSize(4)
                .build();

        pagedListLiveData = new LivePagedListBuilder<>(factory, config).build();

    }

    public void retry() {
        factory.getDataSourceLiveData().getValue().retry();
    }

    public void refresh() {
        factory.getDataSourceLiveData().getValue().invalidate();
    }

    public LiveData<NetworkState> getNetworkState() {
        return Transformations.switchMap(factory.getDataSourceLiveData(), MoviesKeyedDataSource::getNetworkState);
    }

    public LiveData<NetworkState> getRefreshState() {
        return Transformations.switchMap(factory.getDataSourceLiveData(), MoviesKeyedDataSource::getInitialLoading);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

}
