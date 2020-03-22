package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import android.os.Bundle;
import com.example.movieapplication.databinding.MainActivityBinding;
import com.example.movieapplication.fragments.LoginFragment;
import com.example.movieapplication.fragments.UsersListFragment;
import com.example.movieapplication.navigation.FragmentTransitionManager;
import com.example.movieapplication.viewmodels.MovieActivityViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class MovieActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    MainActivityBinding mainActivityBinding;

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    private MovieActivityViewModel movieActivityViewModel;
    FragmentTransitionManager fragmentTransitionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        setupFragmentMangerWrapper();
        setUpLandingFragment();
    }

    private void setupFragmentMangerWrapper(){
        fragmentTransitionManager = FragmentTransitionManager.getFragmentManager(getSupportFragmentManager());
    }

    private void setUpLandingFragment(){
//        UsersListFragment usersListFragment = (UsersListFragment) UsersListFragment.newInstance();
//        final String tag = UsersListFragment.TAG;

        LoginFragment loginFragment = (LoginFragment) LoginFragment.newInstance();
        final String tag = LoginFragment.TAG;

        Fragment fragment;
        if (fragmentTransitionManager.isFragmentAvailableInBackStack(tag)) {
            fragment = fragmentTransitionManager.getFragment(tag);
            fragmentTransitionManager.attachFragment(fragment, R.id.main_container);
        } else { // add new Instance
            fragment = loginFragment;
            fragmentTransitionManager.addFragment(fragment, tag, R.id.main_container);
        }
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }


}