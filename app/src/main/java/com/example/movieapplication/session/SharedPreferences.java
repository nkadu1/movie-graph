package com.example.movieapplication.session;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Map;

public class SharedPreferences {

    protected android.content.SharedPreferences sharedPreferences;

    public SharedPreferences(android.content.SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    protected String putObject(String key, Object object) {
        String json = objectToJson(object);
        putObjectInSharedPreferences(key, json);
        return json;
    }

    private String objectToJson(Object object) {

        return new Gson().toJson(object);
    }

    private void putObjectInSharedPreferences(String key, String jsonObject) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, jsonObject);
        editor.apply();
    }

    protected Map<String, ?> getAll(){
        return sharedPreferences.getAll();
    }

    protected <T> T getObject(String key, Class targetClass) {

        String jsonObject = sharedPreferences.getString(key, "");
        if (TextUtils.isEmpty(jsonObject)) {
            return null;
        }

        return (T) new Gson().fromJson(jsonObject, targetClass);
    }

    public void clear() {

        android.content.SharedPreferences.Editor editor = sharedPreferences.edit().clear();
        editor.apply();
    }
}
