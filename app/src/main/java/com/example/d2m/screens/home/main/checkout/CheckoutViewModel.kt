package com.example.d2m.screens.home.main.checkout

import androidx.lifecycle.MutableLiveData
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.screens.utils.base_classes.BaseViewModel

class CheckoutViewModel : BaseViewModel() {
    var addedCartServices: MutableLiveData<ArrayList<ServiceX>> =
        MutableLiveData<ArrayList<ServiceX>>()
}