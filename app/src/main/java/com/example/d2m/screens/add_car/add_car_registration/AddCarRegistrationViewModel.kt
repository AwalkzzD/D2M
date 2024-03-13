package com.example.d2m.screens.add_car.add_car_registration

import androidx.lifecycle.MutableLiveData
import com.example.d2m.data.local.car.CarRegistration
import com.example.d2m.screens.utils.BaseViewModel

class AddCarRegistrationViewModel : BaseViewModel() {

    var carRegistration = MutableLiveData<CarRegistration>()

}