package com.example.d2m.screens.home.main.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.d2m.data.local.home.ServiceX

class ServiceViewModel : ViewModel() {

    var serviceXLiveData: MutableLiveData<List<ServiceX>> = MutableLiveData<List<ServiceX>>()
    var addedServiceX: MutableList<ServiceX> = mutableListOf()

    fun addService(serviceX: ServiceX) {
        addedServiceX.add(serviceX)
        Log.d("TAG", "addService: $addedServiceX")
    }

    fun removeService(serviceX: ServiceX) {
        addedServiceX.remove(serviceX)
        Log.d("TAG", "removeService: $addedServiceX")
    }
}