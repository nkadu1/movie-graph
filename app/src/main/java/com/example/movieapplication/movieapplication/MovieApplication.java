package com.example.movieapplication.movieapplication;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import javax.inject.Inject;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.DispatchingAndroidInjector_Factory;
import dagger.android.HasActivityInjector;
import dagger.android.HasFragmentInjector;

public class MovieApplication extends Application implements HasActivityInjector{


    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<androidx.fragment.app.Fragment> dispatchingAndroidInjectorFragment;

    @Override
    public void onCreate() {
        super.onCreate();
         //performs field injection on the this class
        AndroidInjector<MovieApplication> applicationInjector = applicationInjector();
        applicationInjector.inject(this);
    }

    protected AndroidInjector<MovieApplication> applicationInjector() {
        return DaggerMovieApplicationComponent.builder().create(this);
    }

    /**
     * Provides the Android Injector that perform field injection on Activities
     * @return
     */
    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

}
