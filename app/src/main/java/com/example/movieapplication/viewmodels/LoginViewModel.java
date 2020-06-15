package com.example.movieapplication.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.movieapplication.repository.LoginRepository;
import com.example.movieapplication.repository.SingleLiveEvent;
import com.example.movieapplication.session.SessionUseCase;
import com.example.type.User;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private LoginRepository loginRepository;
    private SessionUseCase sessionUseCase;

    @Inject
    public LoginViewModel(LoginRepository loginRepository, SessionUseCase sessionUseCase){
        this.loginRepository = loginRepository;
        this.sessionUseCase = sessionUseCase;
    }

    public void getValidRequestToken(){
        loginRepository.getValidRequestToken();
    }

    public void validateLogin(String username, String password){
        User user = User.builder()
                .username(username)
                .password(password)
                .request_token(sessionUseCase.getSession().getRequestToken()).build();
        loginRepository.validateLogin(user);
    }

    public void checkIfValidSessionExists(){
       loginRepository.validateLogin(sessionUseCase.getSession().getUser());
    }

    public void getSessionId(){
        loginRepository.getSessionId();
    }

    public void logout(){
        loginRepository.logout();
    }

    public SingleLiveEvent<Boolean> getValidateLoginLiveData(){
        return loginRepository.getValidateLoginLiveData();
    }

    public MutableLiveData<String> getValidateRequestTokenLiveData() {
        return loginRepository.getValidateRequestTokenLiveData();
    }

    @Override
    public void onCleared() {
        loginRepository.dispose();
    }

}

