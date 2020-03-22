package com.example.movieapplication.session;

import android.content.Context;

import javax.inject.Inject;

public class SessionDataSource extends SharedPreferences{
    private static final String SESSION_DATA_SOURCE = "com.example.movieapplication.SESSION";
    private static final String SESSION_KEY = "MOVIEAPP_SESSION_KEY";

    @Inject
    public SessionDataSource(Context context) {
        super(context.getSharedPreferences(SESSION_DATA_SOURCE, Context.MODE_PRIVATE));
    }

    public void putSession(Session session) {
        putObject(SESSION_KEY, session);
    }

    public Session getSession() {
        return getObject(SESSION_KEY, Session.class);
    }
}
