package com.example.movieapplication.module;


import android.content.Context;

import com.example.movieapplication.MovieActivity;
import com.example.movieapplication.movieapplication.MovieApplication;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Providers for dependencies that cannot use constructor injection.
 */
@Module
public abstract class MovieModule {

    /*
        Generates the sub-component needed for performing field injection on the HomeScreenActivity
     */
    @ContributesAndroidInjector(modules = {
            AppViewModelModule.class,
            AppModule.class
    })
    abstract MovieActivity contributeActivityInjector();

    @Provides
    static Context provideContext(MovieApplication planItApplication){
        return planItApplication.getApplicationContext();
    }

}
