package com.example.d2m.screens.addcar.add_car_brand

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.d2m.data.models.car.CarBrand

class AddCarBrandViewModel : ViewModel() {
    private var carBrandLiveData: MutableLiveData<List<CarBrand>> = MutableLiveData()

    fun postLiveData(carBrandList: List<CarBrand>) {
        carBrandLiveData.postValue(carBrandList)
    }

    fun getLiveData(): MutableLiveData<List<CarBrand>> = carBrandLiveData
}