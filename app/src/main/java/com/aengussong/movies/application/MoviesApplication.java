package com.aengussong.movies.application;

import com.aengussong.movies.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MoviesApplication extends DaggerApplication {


    @Inject
    public MoviesApplication() {
    }


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}