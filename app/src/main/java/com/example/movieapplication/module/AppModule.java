package com.example.movieapplication.module;


import com.example.movieapplication.fragments.LoginFragment;
import com.example.movieapplication.fragments.UsersListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AppModule {

    @ContributesAndroidInjector(modules = {AppViewModelModule.class})
    abstract UsersListFragment contributeUsersListFragment();

    @ContributesAndroidInjector(modules = {AppViewModelModule.class})
    abstract LoginFragment contributeLoginFragment();



}
