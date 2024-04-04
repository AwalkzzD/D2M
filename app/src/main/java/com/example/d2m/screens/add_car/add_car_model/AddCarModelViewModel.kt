package com.example.d2m.screens.add_car.add_car_model

import androidx.lifecycle.MutableLiveData
import com.example.d2m.data.local.car.CarModel
import com.example.d2m.screens.utils.base_classes.BaseViewModel

class AddCarModelViewModel : BaseViewModel() {

    private var carModelLiveData: MutableLiveData<List<CarModel>> = MutableLiveData()

    fun postLiveData(carModelList: List<CarModel>) {
        carModelLiveData.postValue(carModelList)
    }

}