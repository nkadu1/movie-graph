package com.example.movieapplication.viewmodels;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.movieapplication.repository.LoginRepository;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private LoginRepository loginRepository;

    @Inject
    public LoginViewModel(LoginRepository loginRepository){
        this.loginRepository = loginRepository;
    }

    public void validateLogin(String username, String password){

    }

    public void getValidRequestToken(){
        loginRepository.getValidRequestToken();
    }

}

