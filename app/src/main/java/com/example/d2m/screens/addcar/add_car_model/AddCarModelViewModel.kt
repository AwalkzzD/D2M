package com.example.d2m.screens.addcar.add_car_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.d2m.data.models.car.CarModel

class AddCarModelViewModel : ViewModel() {
    private var carModelLiveData: MutableLiveData<List<CarModel>> = MutableLiveData()

    fun postLiveData(carModelList: List<CarModel>) {
        carModelLiveData.postValue(carModelList)
    }
}