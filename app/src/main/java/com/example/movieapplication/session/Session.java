package com.example.movieapplication.session;


import com.example.type.User;

public class Session {

    private User user;
    private String sessionId;

    public Session(){
    }

    public User getUser() {
        return this.user;
    }

    public String getRequestToken() {
        if(user != null){
            return user.request_token();
        }
        return "";
    }

    public void setUser(User newUser) {
        this.user = User.builder().username(newUser.username()).password(newUser.password()).request_token(newUser.request_token()).build();
    }

    public void setRequestToken(String requestToken) {
        this.user = User.builder().username(user.username()).password(user.password()).request_token(requestToken).build();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
