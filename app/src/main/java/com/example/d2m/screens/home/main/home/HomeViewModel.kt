package com.example.d2m.screens.home.main.home

import androidx.lifecycle.MutableLiveData
import com.example.d2m.data.local.home.Banner
import com.example.d2m.data.local.home.Service
import com.example.d2m.screens.utils.BaseViewModel

class HomeViewModel : BaseViewModel() {

    var bannerLiveData: MutableLiveData<List<Banner>> = MutableLiveData<List<Banner>>()
    var serviceLiveData: MutableLiveData<List<Service>> = MutableLiveData<List<Service>>()

}