package com.example.d2m.screens.addcar.add_fuel_type

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.d2m.data.local.car.FuelType

class AddFuelTypeViewModel : ViewModel() {
    private var fuelTypeLiveData: MutableLiveData<List<FuelType>> = MutableLiveData()

    fun postLiveData(fuelTypeList: List<FuelType>) {
        fuelTypeLiveData.postValue(fuelTypeList)
    }
}