package com.aengussong.movies.di;

import com.aengussong.movies.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
