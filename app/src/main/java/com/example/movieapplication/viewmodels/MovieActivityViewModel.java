package com.example.movieapplication.viewmodels;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ValidateLoginMutation;

public class MovieActivityViewModel  extends ViewModel {

    private MutableLiveData<Fragment> fragmentMutableLiveData = new MutableLiveData<>();

    public void setFragment(Fragment fragment){
        fragmentMutableLiveData.postValue(fragment);
    }

    public MutableLiveData<Fragment> getFragmentMutableLiveData() {
        return fragmentMutableLiveData;
    }
}
