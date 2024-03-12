package com.example.d2m.screens.addcar.add_car_registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.d2m.R
import com.example.d2m.data.local.car.CarRegistration
import com.example.d2m.databinding.FragmentAddCarRegistrationBinding
import com.example.d2m.screens.addcar.AddCarViewModel

class AddCarRegistrationFragment : Fragment() {

    private lateinit var addCarRegistrationBinding: FragmentAddCarRegistrationBinding
    private lateinit var carRegistration: CarRegistration

    private val addCarRegistrationViewModel: AddCarRegistrationViewModel by activityViewModels()
    private val addCarViewModel: AddCarViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        addCarRegistrationBinding = FragmentAddCarRegistrationBinding.inflate(inflater)
        return addCarRegistrationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        addCarRegistrationBinding.addCar.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_addCarRegistrationFragment_to_addCarBrandFragment)
            }
        }

        addCarRegistrationBinding.vehicleNumber.apply {
            addTextChangedListener {
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?, start: Int, count: Int, after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?, start: Int, before: Int, count: Int
                    ) {
                    }

                    override fun afterTextChanged(s: Editable) {

                    }
                }
            }
        }
    }

    private fun initViewModel() {
        carRegistration = CarRegistration(addCarRegistrationBinding.vehicleNumber.text.toString())

        addCarRegistrationViewModel.carRegistration.postValue(carRegistration)

        addCarViewModel.carRegistration.value = carRegistration
        addCarViewModel.vehicleColor.value = "xyz"
    }
}