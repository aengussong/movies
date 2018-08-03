package com.aengussong.movies.viewModel.viewModelDi;

import android.arch.lifecycle.ViewModel;

import com.aengussong.movies.viewModel.MainActivityViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel bindUserViewModel(MainActivityViewModel mainActivityViewModel);
}
