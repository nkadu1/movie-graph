package com.example.movieapplication.appolloclient;

import com.apollographql.apollo.ApolloClient;

import javax.inject.Singleton;

import okhttp3.OkHttpClient;


public class ApolloClientConnector {

    private static final String BASE_URL = "http://10.0.2.2:4000/";
    private static ApolloClient apolloClient;

    private static ApolloClient buildApolloClientInstance(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
         apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        return apolloClient;
    }

    public static ApolloClient getApolloClient() {
        if ( apolloClient== null) {
            apolloClient = buildApolloClientInstance();
        }
        return apolloClient;
    }

}
