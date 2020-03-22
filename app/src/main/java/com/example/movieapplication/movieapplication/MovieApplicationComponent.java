package com.example.movieapplication.movieapplication;

import com.example.movieapplication.module.AppModule;
import com.example.movieapplication.module.AppViewModelModule;
import com.example.movieapplication.module.MovieModule;

import javax.inject.Singleton;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Performs dagger android field injection on PlanItApplication class.
 * <p>
 * AndroidInjector reduces the boilder plate code needed to inject into Android Framework component
 * classes.
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        MovieModule.class,
        AppModule.class,
        AppViewModelModule.class

})
public interface MovieApplicationComponent extends AndroidInjector<MovieApplication> {
    /*
       Builds the AndroidInjector that is used to perform field injection on PlanItApplication class.
     */
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MovieApplication> {
    }


}
