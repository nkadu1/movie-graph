package com.example.movieapplication.session;

import com.example.type.User;

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
            session.setUser(user);
            sessionDataSource.putSession(session);
    }

    public void saveRequestToken(String requestToken){
        session.setRequestToken(requestToken);
        sessionDataSource.putSession(session);
    }

    public Session getSession() {
        if(sessionDataSource.getSession() == null){
            return session;
        }
        return sessionDataSource.getSession();
    }

    public void saveSessionId(String sessionId){
        if(!sessionId.equals(getSession().getSessionId())){
            session.setSessionId(sessionId);
            sessionDataSource.putSession(session);
        }
    }

}
