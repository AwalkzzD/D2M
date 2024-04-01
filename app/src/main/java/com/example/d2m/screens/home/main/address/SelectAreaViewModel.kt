package com.example.d2m.screens.home.main.address

import androidx.lifecycle.MutableLiveData
import com.example.d2m.data.remote.otp.verify.GetCityAreaDetail
import com.example.d2m.screens.utils.BaseViewModel

class SelectAreaViewModel : BaseViewModel() {

    var area: MutableLiveData<GetCityAreaDetail> = MutableLiveData<GetCityAreaDetail>()

}