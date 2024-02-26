package com.example.d2m.screens.addcar.add_car_registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.d2m.R
import com.example.d2m.databinding.FragmentAddCarRegistrationBinding
import com.example.d2m.screens.addcar.viewmodel.AddCarViewModel

class AddCarRegistrationFragment : Fragment() {

    private lateinit var addCarRegistrationBinding: FragmentAddCarRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        addCarRegistrationBinding = FragmentAddCarRegistrationBinding.inflate(inflater)
        return addCarRegistrationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addCarRegistrationBinding.addCar.apply {
            setOnClickListener {
                addData()
                findNavController().navigate(R.id.action_addCarRegistrationFragment_to_addCarBrandFragment)
            }
        }

        addCarRegistrationBinding.vehicleNumber.apply {
            addTextChangedListener {
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?, start: Int, count: Int, after: Int
                    ) {
                        addCarRegistrationBinding.addCar.isEnabled = false
                    }

                    override fun onTextChanged(
                        s: CharSequence?, start: Int, before: Int, count: Int
                    ) {

                    }

                    override fun afterTextChanged(s: Editable) {
                        if (s.length == 10) {
                            addCarRegistrationBinding.addCar.isEnabled = true
                        }
                    }
                }
            }
        }
    }

    private fun addData() {
        val addCarViewModel = ViewModelProvider(requireActivity())[AddCarViewModel::class.java]
        addCarViewModel.vehicleNumber.value =
            addCarRegistrationBinding.vehicleNumber.text.toString()
    }
}