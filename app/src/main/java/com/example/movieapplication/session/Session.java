package com.example.movieapplication.session;

import com.example.movieapplication.model.User;

public class Session {

    private User user;
    private String requestToken;

    public Session(){
        this.user = new User();
    }

    public User getUser() {
        return this.user;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }
}
