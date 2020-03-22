package com.example.movieapplication.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.fetcher.ApolloResponseFetchers;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.example.GetUsersCollectionQuery;
import com.example.movieapplication.appolloclient.ApolloClientConnector;
import com.example.movieapplication.constants.Account;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UsersListRepository {


    private MutableLiveData<List<GetUsersCollectionQuery.GetAllListByUser>> usersListCollectionLiveData = new MutableLiveData<>();
    private ApolloClient apolloClient;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public UsersListRepository(Context applicationContext) {
        apolloClient = ApolloClientConnector.getApolloClient(applicationContext);
    }

    public void fetchUsersCollection() {
        final GetUsersCollectionQuery usersCollectionQuery = GetUsersCollectionQuery.builder()
                .accountId(Account.ACCOUNT_ID)
                .build();

        ApolloCall<GetUsersCollectionQuery.Data> userListCall = apolloClient
                .query(usersCollectionQuery)
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST);

        disposable.add(Rx2Apollo.from(userListCall)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {OnSuccess(response);},this::onError));
    }

    private void OnSuccess(Response<GetUsersCollectionQuery.Data> response){
        usersListCollectionLiveData.postValue(usersCollectionRepository(response));
    }

    private void onError(Throwable err){
        Timber.e(err);
    }


    private List<GetUsersCollectionQuery.GetAllListByUser> usersCollectionRepository(Response<GetUsersCollectionQuery.Data> response) {
        List<GetUsersCollectionQuery.GetAllListByUser> usersCollection = new ArrayList<>();
        final GetUsersCollectionQuery.Data responseData = response.data();
        if (responseData == null) {
            return Collections.emptyList();
        }
        final List<GetUsersCollectionQuery.GetAllListByUser> medialists = responseData.getAllListByUser();
        if (medialists == null) {
            return Collections.emptyList();
        }
        for (GetUsersCollectionQuery.GetAllListByUser entry : medialists) {
            usersCollection.add(entry);
        }
        return usersCollection;
    }


    public LiveData<List<GetUsersCollectionQuery.GetAllListByUser>> getUsersListCollectionLiveData() {
        return usersListCollectionLiveData;
    }

    public void dispose(){
        disposable.dispose();
    }
}
