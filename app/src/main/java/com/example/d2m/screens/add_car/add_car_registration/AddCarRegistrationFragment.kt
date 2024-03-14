package com.example.d2m.screens.add_car.add_car_registration

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.d2m.R
import com.example.d2m.data.local.car.CarRegistration
import com.example.d2m.databinding.FragmentAddCarRegistrationBinding
import com.example.d2m.screens.add_car.AddCarViewModel
import com.example.d2m.screens.utils.BaseActivity
import com.example.d2m.screens.utils.BaseFragment

class AddCarRegistrationFragment :
    BaseFragment<FragmentAddCarRegistrationBinding, AddCarRegistrationViewModel>(
        R.layout.fragment_add_car_registration, AddCarRegistrationViewModel::class.java
    ) {

    private lateinit var carRegistration: CarRegistration

    private val addCarViewModel: AddCarViewModel by activityViewModels()

    override fun setUpView() {
        setUpToolBar()
        setupListeners()
    }

    private fun setUpToolBar() {
        (activity as BaseActivity<*, *>).customiseToolbar("Add Car", false)
    }

    private fun setupListeners() {

        fragmentBinding.addCar.apply {
            setOnClickListener {
                addCarRegistration()
                findNavController().navigate(R.id.action_addCarRegistrationFragment_to_addCarBrandFragment)
            }
        }

    }

    private fun addCarRegistration() {
        carRegistration = CarRegistration(fragmentBinding.vehicleNumber.text.toString())

        fragmentViewModel.carRegistration.postValue(carRegistration)
        addCarViewModel.carRegistration.value = carRegistration
        addCarViewModel.vehicleColor.value = "xyz"
    }

}