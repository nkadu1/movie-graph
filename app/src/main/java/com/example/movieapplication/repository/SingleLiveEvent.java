package com.example.movieapplication.repository;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class SingleLiveEvent<T> extends MutableLiveData<T> {
    private MediatorLiveData<T> liveDataToObserve;
    private final MutableLiveData<Boolean> mPending = new MutableLiveData<>();

    public SingleLiveEvent() {
        liveDataToObserve = new MediatorLiveData<>();
        liveDataToObserve.addSource(this, currentValue -> {
            liveDataToObserve.postValue(currentValue);
            mPending.postValue(null);
        });
    }

    private boolean isPending() {
        return mPending.getValue()!=null;
    }

    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        liveDataToObserve.observe(owner, t -> {
            if(isPending()) {
                observer.onChanged(t);
            }
        });
    }

    @MainThread
    @Override
    public void setValue(T value) {
        mPending.setValue(true);
        super.setValue(value);
    }

    @MainThread
    public void call(){
        setValue(null);
    }

}