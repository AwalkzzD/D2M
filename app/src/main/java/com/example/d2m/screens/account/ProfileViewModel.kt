package com.example.d2m.screens.account

import androidx.lifecycle.MutableLiveData
import com.example.d2m.data.local.account.UserProfile
import com.example.d2m.screens.utils.BaseViewModel

class ProfileViewModel : BaseViewModel() {

    private var profileLiveData: MutableLiveData<UserProfile> = MutableLiveData<UserProfile>()

    fun loadUserProfile(
        userID: String,
        token: String,
        email: String,
        totalCars: String,
        fullName: String,
        isSubscribedUser: String,
        subscribePlanName: String
    ) {
        profileLiveData.postValue(
            UserProfile(
                userID, token, email, totalCars, fullName, isSubscribedUser, subscribePlanName
            )
        )
    }

    fun getProfileData(): UserProfile? {
        return profileLiveData.value
    }

}