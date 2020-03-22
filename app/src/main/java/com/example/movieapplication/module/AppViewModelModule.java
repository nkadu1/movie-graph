package com.example.movieapplication.module;
import androidx.lifecycle.ViewModel;

import com.example.movieapplication.viewmodels.LoginViewModel;
import com.example.movieapplication.viewmodels.UsersListFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

/**
 * Providers for dependencies that cannot use constructor injection.
 */
@Module
public abstract class AppViewModelModule {

    @Binds
    @IntoMap
    @ClassKey(UsersListFragmentViewModel.class)
    public abstract ViewModel provideUsersListFragmentViewModel(UsersListFragmentViewModel usersListFragmentViewModel);

    @Binds
    @IntoMap
    @ClassKey(LoginViewModel.class)
    public abstract ViewModel provideLoginViewModel(LoginViewModel loginViewModel);


}
