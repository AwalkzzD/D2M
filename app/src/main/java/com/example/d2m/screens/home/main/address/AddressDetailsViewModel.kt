package com.example.d2m.screens.home.main.address

import androidx.lifecycle.MutableLiveData
import com.example.d2m.screens.utils.BaseViewModel

class AddressDetailsViewModel : BaseViewModel() {
    var area: MutableLiveData<String> = MutableLiveData<String>()
}