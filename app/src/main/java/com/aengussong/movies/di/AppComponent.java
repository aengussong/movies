package com.aengussong.movies.di;

import com.aengussong.movies.MoviesApplication;
import com.aengussong.movies.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {ApplicationModule.class,
        ActivityBuilder.class,
        NetworkModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<MoviesApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MoviesApplication> {

    }
}
