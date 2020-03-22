package com.example.movieapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movieapplication.adapters.UsersListAdapter;
import com.example.movieapplication.databinding.UserListFragmentBinding;
import com.example.movieapplication.dagger.DaggerViewModelFactory;
import com.example.movieapplication.viewmodels.UsersListFragmentViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class UsersListFragment extends Fragment {
    public static final String TAG = "Users_List_Fragment";

    private UserListFragmentBinding userListFragmentBinding;
    private UsersListAdapter usersListAdapter;
    private UsersListFragmentViewModel usersListFragmentViewModel;
    @Inject
    DaggerViewModelFactory daggerViewModelFactory;

    public static Fragment newInstance() {
        UsersListFragment usersListFragment = new UsersListFragment();
        Bundle args = new Bundle();
        args.putString("TAG", TAG);
        usersListFragment.setArguments(args);
        return new UsersListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userListFragmentBinding = UserListFragmentBinding.inflate(inflater, container, false);
        setUpViewModels();
        setUpUsersListAdapter();
        usersListFragmentViewModel.fetchUsersCollection();
        return userListFragmentBinding.getRoot();
    }

    private void setUpViewModels(){
        this.usersListFragmentViewModel = ViewModelProviders.of(this, daggerViewModelFactory).get(UsersListFragmentViewModel.class);
        usersListFragmentViewModel.getUsersListCollectionLiveData().observe(getViewLifecycleOwner(),usersCollection -> {usersListAdapter.setUserList(usersCollection);});
    }

    private void setUpUsersListAdapter(){
        usersListAdapter = new UsersListAdapter();
        userListFragmentBinding.userList.setHasFixedSize(true);
        userListFragmentBinding.userList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        userListFragmentBinding.userList.setLayoutManager(new LinearLayoutManager(getContext()));
        userListFragmentBinding.userList.setAdapter(usersListAdapter);
    }


    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }


}
