package com.example.movieapplication.session;

import com.example.movieapplication.model.User;

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

    public void saveLoggedInUser(User user){
        if(!user.equals(session.getUser())){
            session.setUser(user);
            sessionDataSource.putSession(session);
        }
    }

    public void saveRequestToken(String requestToken){
        session.setRequestToken(requestToken);
        sessionDataSource.putSession(session);
    }

    public Session getSession() {
        return sessionDataSource.getSession();
    }


}
