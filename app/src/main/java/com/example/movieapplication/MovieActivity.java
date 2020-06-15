package com.example.movieapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toolbar;

import com.example.movieapplication.dagger.DaggerViewModelFactory;
import com.example.movieapplication.databinding.MainActivityBinding;
import com.example.movieapplication.fragments.LoginFragment;
import com.example.movieapplication.fragments.UsersListFragment;
import com.example.movieapplication.navigation.FragmentTransitionManager;
import com.example.movieapplication.repository.SingleLiveEvent;
import com.example.movieapplication.viewmodels.LoginViewModel;
import com.example.movieapplication.viewmodels.MovieActivityViewModel;
import javax.inject.Inject;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;


public class MovieActivity extends AppCompatActivity implements HasSupportFragmentInjector, LifecycleOwner {

    MainActivityBinding mainActivityBinding;

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;
    @Inject
    DaggerViewModelFactory daggerViewModelFactory;
    private MovieActivityViewModel movieActivityViewModel;
    private LoginViewModel loginViewModel;
    FragmentTransitionManager fragmentTransitionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        setUpViewModel();
        setupFragmentMangerWrapper();
    }

    @Override
    protected void onResume() {
        super.onResume();
        observeRequestToken();
        setUpLandingFragment();
    }

    private void setUpViewModel(){
        this.loginViewModel = ViewModelProviders.of(this, daggerViewModelFactory).get(LoginViewModel.class);
        this.movieActivityViewModel  = ViewModelProviders.of(this).get(MovieActivityViewModel.class);
        movieActivityViewModel.getFragmentMutableLiveData().observe(this, fragment -> {
            String tag = fragment.getArguments().getString("TAG");
            if(TextUtils.isEmpty(tag)){
                Timber.d("no tag exists for the new fragment");
                return;
            }
            launchFragment(fragment, tag);
        });
    }

    private void setUpLandingFragment(){
        SingleLiveEvent<Boolean> validateLoginLiveData = loginViewModel.getValidateLoginLiveData();
        if(validateLoginLiveData.hasActiveObservers()){
            validateLoginLiveData.removeObservers(this);
        }
        validateLoginLiveData.observe(this, status -> {
            if (status) {
                movieActivityViewModel.setFragment(UsersListFragment.newInstance());
            } else {
                movieActivityViewModel.setFragment(LoginFragment.newInstance());
            }
        });
    }

    private void observeRequestToken(){
        loginViewModel.getValidRequestToken();
        MutableLiveData<String> tokenLiveData = loginViewModel.getValidateRequestTokenLiveData();
        tokenLiveData.removeObservers(this::getLifecycle);
        if(!tokenLiveData.hasActiveObservers()){
            tokenLiveData.observe(this, token -> {
                loginViewModel.checkIfValidSessionExists();
            });
        }
    }

    private void setupFragmentMangerWrapper(){
        fragmentTransitionManager = FragmentTransitionManager.getFragmentManager(getSupportFragmentManager());
    }

    private void launchFragment(Fragment newFragment, String tag){
        fragmentTransitionManager.addFragment(newFragment, tag, R.id.main_container);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

}