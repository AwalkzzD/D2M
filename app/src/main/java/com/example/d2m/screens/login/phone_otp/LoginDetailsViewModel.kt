package com.example.d2m.screens.login.phone_otp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginDetailsViewModel : ViewModel() {
    var phoneNum = MutableLiveData<String>()
    var getWhatsappUpdates = MutableLiveData<String>()
}