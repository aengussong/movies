package com.aengussong.movies.di;

import com.aengussong.movies.MoviesApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {ApplicationModule.class,
        ActivityBuilder.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<MoviesApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MoviesApplication> {

    }
}
