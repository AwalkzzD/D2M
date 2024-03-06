package com.example.d2m.screens.home.main.service

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.d2m.data.local.home.ServiceX

class ServiceViewModel : ViewModel() {

    var serviceXLiveData: MutableLiveData<List<ServiceX>> = MutableLiveData<List<ServiceX>>()
    var addedServiceX: ObservableArrayList<ServiceX> = ObservableArrayList<ServiceX>()
    var isEmpty: MutableLiveData<Boolean> = MutableLiveData<Boolean>(true)

    fun addService(serviceX: ServiceX) {
        addedServiceX.add(serviceX)
        isEmpty.postValue(false)
    }

    fun removeService(serviceX: ServiceX) = addedServiceX.remove(serviceX)

    fun removeAllServices() {
        addedServiceX.clear()
        isEmpty.value = true
    }

}