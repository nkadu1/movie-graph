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


    private MutableLiveData<List<GetUsersCollectionQuery.GetUsersListCollection>> usersListCollectionLiveData = new MutableLiveData<>();
    private ApolloClient apolloClient;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public UsersListRepository() {
        apolloClient = ApolloClientConnector.getApolloClient();
    }

    ApolloCall<GetUsersCollectionQuery.Data> userListCall;
    public void fetchUsersCollection() {
        final GetUsersCollectionQuery usersCollectionQuery = GetUsersCollectionQuery.builder()
                .accountId(Account.ACCOUNT_ID)
                .build();
        if(userListCall !=null){
            userListCall.cancel();
        }

        userListCall = apolloClient
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


    private List<GetUsersCollectionQuery.GetUsersListCollection> usersCollectionRepository(Response<GetUsersCollectionQuery.Data> response) {
        List<GetUsersCollectionQuery.GetUsersListCollection> usersCollection = new ArrayList<>();
        final GetUsersCollectionQuery.Data responseData = response.data();
        if (responseData == null) {
            return Collections.emptyList();
        }
        final List<GetUsersCollectionQuery.GetUsersListCollection> medialists = responseData.getUsersListCollection();
        if (medialists == null) {
            return Collections.emptyList();
        }
        for (GetUsersCollectionQuery.GetUsersListCollection entry : medialists) {
            usersCollection.add(entry);
        }
        return usersCollection;
    }


    public LiveData<List<GetUsersCollectionQuery.GetUsersListCollection>> getUsersListCollectionLiveData() {
        return usersListCollectionLiveData;
    }

    public void dispose(){
        disposable.dispose();
    }
}
