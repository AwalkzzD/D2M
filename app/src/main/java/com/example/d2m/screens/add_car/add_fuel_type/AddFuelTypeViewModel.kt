package com.example.d2m.screens.add_car.add_fuel_type

import androidx.lifecycle.MutableLiveData
import com.example.d2m.data.local.car.FuelType
import com.example.d2m.screens.utils.BaseViewModel

class AddFuelTypeViewModel : BaseViewModel() {

    private var fuelTypeLiveData: MutableLiveData<List<FuelType>> = MutableLiveData()

    fun postLiveData(fuelTypeList: List<FuelType>) {
        fuelTypeLiveData.postValue(fuelTypeList)
    }

}