package com.example.d2m.screens.home.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.d2m.data.models.home.Banner
import com.example.d2m.data.models.home.Service

class HomeViewModel : ViewModel() {
    var bannerLiveData: MutableLiveData<List<Banner>> = MutableLiveData<List<Banner>>()
    var serviceLiveData: MutableLiveData<List<Service>> = MutableLiveData<List<Service>>()
}