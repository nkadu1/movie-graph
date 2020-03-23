package com.example.movieapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.GetUsersCollectionQuery;
import com.example.movieapplication.repository.UsersListRepository;
import java.util.List;
import javax.inject.Inject;

public class UsersListFragmentViewModel extends ViewModel {

    private UsersListRepository usersListRepository;

    @Inject
    public UsersListFragmentViewModel(UsersListRepository usersListRepository){
        this.usersListRepository = usersListRepository;
    }

    public void fetchUsersCollection() {
       usersListRepository.fetchUsersCollection();
    }

    public LiveData<List<GetUsersCollectionQuery.GetUsersListCollection>> getUsersListCollectionLiveData() {
        return usersListRepository.getUsersListCollectionLiveData();
    }

    @Override
    protected void onCleared() {
        usersListRepository.dispose();
    }
}
