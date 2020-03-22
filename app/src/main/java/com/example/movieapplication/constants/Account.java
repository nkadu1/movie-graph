package com.example.movieapplication.constants;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static com.example.movieapplication.constants.Account.ACCOUNT_ID;

@StringDef({ACCOUNT_ID})
@Retention(RetentionPolicy.SOURCE)
public @interface Account {
    String ACCOUNT_ID = "5e6d596f459ad6001863a5e8";
    String API_KEY = "069224c3a98cc00198b789de95c33474";

}
