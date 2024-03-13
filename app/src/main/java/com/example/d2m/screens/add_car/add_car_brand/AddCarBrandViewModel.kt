package com.example.d2m.screens.add_car.add_car_brand

import androidx.lifecycle.MutableLiveData
import com.example.d2m.data.local.car.CarBrand
import com.example.d2m.screens.utils.BaseViewModel

class AddCarBrandViewModel : BaseViewModel() {

    private var carBrandLiveData: MutableLiveData<List<CarBrand>> = MutableLiveData()

    fun postLiveData(carBrandList: List<CarBrand>) {
        carBrandLiveData.postValue(carBrandList)
    }

}