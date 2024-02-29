package com.example.d2m.screens.home.main.service

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.d2m.data.models.home.ServiceX

class ServiceViewModel : ViewModel() {
    var serviceXLiveData: MutableLiveData<List<ServiceX>> = MutableLiveData<List<ServiceX>>()
}