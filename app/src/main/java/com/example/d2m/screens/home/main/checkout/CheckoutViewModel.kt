package com.example.d2m.screens.home.main.checkout

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.d2m.data.local.checkout.TimeSlots
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.screens.utils.base_classes.BaseViewModel

class CheckoutViewModel : BaseViewModel() {
    var addedCartServices: MutableLiveData<ArrayList<ServiceX>> =
        MutableLiveData<ArrayList<ServiceX>>()

    var timeSlots: ObservableField<TimeSlots> = ObservableField()

}