package com.example.d2m.screens.login.phone_otp

import androidx.lifecycle.MutableLiveData
import com.example.d2m.screens.utils.BaseViewModel

class LoginDetailsViewModel : BaseViewModel() {

    var phoneNum = MutableLiveData<String>()
    var getWhatsappUpdates = MutableLiveData<String>()

}