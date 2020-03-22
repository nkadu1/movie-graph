package com.example.movieapplication.repository;

import android.content.Context;

import com.apollographql.apollo.ApolloClient;
import com.example.movieapplication.appolloclient.ApolloClientConnector;

import javax.inject.Inject;

public class LoginRepository {

    private ApolloClient apolloClient;

    @Inject
    public LoginRepository(Context applicationContext){
        apolloClient = ApolloClientConnector.getApolloClient(applicationContext);
    }

    public void validateLogin(String username, String password){

    }

    public void getValidRequestToken(){

    }

}
