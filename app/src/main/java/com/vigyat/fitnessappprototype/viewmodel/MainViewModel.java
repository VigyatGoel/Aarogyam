package com.vigyat.fitnessappprototype.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Integer> stepCount;
    private final MutableLiveData<FirebaseUser> userData;

    public MainViewModel() {
        stepCount = new MutableLiveData<>();
        userData = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getStepCount() {
        return stepCount;
    }

    public void setStepCount(int count) {
        stepCount.setValue(count);
    }

    public MutableLiveData<FirebaseUser> getUserData() {
        return userData;
    }

    public void setUserData(FirebaseUser user) {
        userData.setValue(user);
    }
}
