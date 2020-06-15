package com.example.movieapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.movieapplication.MovieActivity;
import com.example.movieapplication.databinding.LoginFragmentBinding;
import com.example.movieapplication.dagger.DaggerViewModelFactory;
import com.example.movieapplication.viewmodels.LoginViewModel;
import com.example.movieapplication.viewmodels.MovieActivityViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class LoginFragment extends Fragment {
    public static final String TAG = "Login_Fragment";
    private LoginFragmentBinding loginFragmentBinding;
    private LoginViewModel loginViewModel;

    @Inject
    DaggerViewModelFactory daggerViewModelFactory;

    public static Fragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString("TAG", TAG);
        loginFragment.setArguments(args);
        return loginFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loginFragmentBinding = LoginFragmentBinding.inflate(inflater, container, false);
        setUpViewModels();
        setUpLayout();
        return loginFragmentBinding.getRoot();
    }

    private void setUpLayout(){
        loginFragmentBinding.btnSubmit.setOnClickListener(onSubmit());
    }

    private View.OnClickListener onSubmit(){
        return v -> {
            String username =  loginFragmentBinding.usernameInput.getText().toString();
            String password =  loginFragmentBinding.passwordInput.getText().toString();
            if(validInput(username,password)){
                loginViewModel.validateLogin(username, password);
            }
        };
    }

    private void setUpViewModels(){
        this.loginViewModel = ViewModelProviders.of(this, daggerViewModelFactory).get(LoginViewModel.class);
        MovieActivityViewModel movieActivityViewModel = ViewModelProviders.of(getActivity()).get(MovieActivityViewModel.class);
        loginViewModel.getValidateLoginLiveData().observe(getViewLifecycleOwner(), status -> {
            if(status){
                movieActivityViewModel.setFragment(UsersListFragment.newInstance());
            }
            else{
                Toast toast = Toast.makeText(getContext(), "Please check your credentials",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,200);
                toast.show();
            }
        });
    }

    private boolean validInput(String username, String password){
        if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
            Toast toast = Toast.makeText(getContext(), "Email and Password is required",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,200);
            toast.show();
            return false;
        }
        else if(TextUtils.isEmpty(username)){
            Toast toast = Toast.makeText(getContext(), "Email is required",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,200);
            toast.show();
            return false;
        }
        else if(TextUtils.isEmpty(password)){
            Toast toast = Toast.makeText(getContext(), "Password is required",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,200);
            toast.show();
            return false;
        }
        return true;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loginViewModel.onCleared();
    }

}
