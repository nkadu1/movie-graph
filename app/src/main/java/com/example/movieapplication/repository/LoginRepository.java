package com.example.movieapplication.repository;

import androidx.lifecycle.MutableLiveData;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.fetcher.ApolloResponseFetchers;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.example.GetRequestTokenQuery;
import com.example.GetSessionIdMutation;
import com.example.LogoutMutation;
import com.example.ValidateLoginMutation;
import com.example.movieapplication.appolloclient.ApolloClientConnector;
import com.example.movieapplication.constants.Account;
import com.example.movieapplication.session.SessionUseCase;
import com.example.type.RequestTokenBody;
import com.example.type.SessionBody;
import com.example.type.User;
import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LoginRepository {

    private SingleLiveEvent<Boolean> validateLoginLiveData = new SingleLiveEvent<>();
    private MutableLiveData<String> validateRequestTokenLiveData = new MutableLiveData<>();
    private ApolloClient apolloClient;
    private SessionUseCase sessionUseCase;
    private CompositeDisposable disposable = new CompositeDisposable();


    @Inject
    public LoginRepository(SessionUseCase sessionUseCase){
        apolloClient = ApolloClientConnector.getApolloClient();
        this.sessionUseCase = sessionUseCase;
    }

    public void validateLogin(User user){
        final ValidateLoginMutation validateLoginMutation = ValidateLoginMutation.builder()
                .api_key(Account.API_KEY)
                .user(user)
                .build();

        ApolloCall<ValidateLoginMutation.Data> validateLoginCall = apolloClient
                .mutate(validateLoginMutation);

        disposable.add(Rx2Apollo.from(validateLoginCall)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {OnSuccess(response,user);},this::onError));
    }

    private void OnSuccess(Response<ValidateLoginMutation.Data> response, User user){
            if(response.data().toString().contains("200")){
                sessionUseCase.saveLoggedInUser(user);
                validateLoginLiveData.postValue(Boolean.TRUE);
            }
            else{
                validateLoginLiveData.postValue(Boolean.FALSE);
            }

    }

    public SingleLiveEvent<Boolean> getValidateLoginLiveData() {
        return validateLoginLiveData;
    }

    private void onError(Throwable err){
        Timber.e(err);
    }

    public void getSessionId(){
        RequestTokenBody requestTokenBody = RequestTokenBody.builder()
                .request_token("")
                .build();

        final GetSessionIdMutation getSessionIdMutation = GetSessionIdMutation.builder()
                .api_key(Account.API_KEY)
                .request_token(requestTokenBody)
                .build();

        ApolloCall<GetSessionIdMutation.Data> getSessionIdCall = apolloClient
                .mutate(getSessionIdMutation);

        disposable.add(Rx2Apollo.from(getSessionIdCall)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {OnGetSessionSuccess(response);},this::onError));
    }

    private void OnGetSessionSuccess(Response<GetSessionIdMutation.Data> response){
        if(response.data().toString().contains("200")){
            sessionUseCase.saveSessionId(response.data().getSessionId().session_id());
            validateLoginLiveData.postValue(Boolean.TRUE);
        }
        else{
            validateLoginLiveData.postValue(Boolean.FALSE);
        }

    }


    public void getValidRequestToken(){
        final GetRequestTokenQuery getRequestTokenQuery = GetRequestTokenQuery.builder()
                .api_key(Account.API_KEY)
                .build();

        ApolloCall<GetRequestTokenQuery.Data> requestNewTokenCall = apolloClient
                .query(getRequestTokenQuery)
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST);

        disposable.add(Rx2Apollo.from(requestNewTokenCall)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {OnValidTokenSuccess(response);},this::onError));

    }

    private void OnValidTokenSuccess(Response<GetRequestTokenQuery.Data> response){
        if(response.data().toString().contains("200")){
             String requestToken   = response.data().getRequestToken().request_token();
             validateRequestTokenLiveData.postValue(requestToken);
             sessionUseCase.saveRequestToken(requestToken);
        }
    }


    public void logout(){
        SessionBody sessionBody = SessionBody.builder()
                .session_id("")
                .build();

        final LogoutMutation logoutMutation = LogoutMutation.builder()
                .api_key(Account.API_KEY)
                .session_id(sessionBody)
                .build();

        ApolloCall<LogoutMutation.Data> logoutCall = apolloClient
                .mutate(logoutMutation);

        disposable.add(Rx2Apollo.from(logoutCall)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {OnLogoutSuccess(response);},this::onError));
    }

    private void OnLogoutSuccess(Response<LogoutMutation.Data> response){
        if(response.data().toString().contains("200")){
            //logout
        }
        else{
            //stay on current page
        }

    }

    public MutableLiveData<String> getValidateRequestTokenLiveData() {
        return validateRequestTokenLiveData;
    }

    public void dispose(){
        disposable.dispose();
    }
}
