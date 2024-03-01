package com.example.d2m.screens.addcar.add_car_registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.d2m.data.local.car.CarRegistration

class AddCarRegistrationViewModel : ViewModel() {
    var carRegistration = MutableLiveData<CarRegistration>()
}