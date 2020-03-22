package com.example.movieapplication.session;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionUseCase {
    private SessionDataSource sessionDataSource;
    private Session session;

    @Inject
    public SessionUseCase( SessionDataSource sessionDataSource){
        this.sessionDataSource = sessionDataSource;
        session = sessionDataSource.getSession();
        //add dagger provider
        if (session == null) {
            session = new Session();
        }
    }

    private void saveLoggedInUser(String userName, String password){

    }

}
