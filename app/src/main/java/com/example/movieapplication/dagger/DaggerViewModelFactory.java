package com.example.movieapplication.dagger;

import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Instantiates ViewModels that use Dagger constructor injection.
 * The default Factory only instantiates ViewModels that have a no-arg constructor.
 */
@Singleton
public class DaggerViewModelFactory implements ViewModelProvider.Factory {

    private Map<Class<?>, Provider<ViewModel>> mViewModels;

    @Inject
    public DaggerViewModelFactory(Map<Class<?>, Provider<ViewModel>> mViewModels) {
        this.mViewModels = mViewModels;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T)mViewModels.get(modelClass).get();
    }
}
