package com.example.d2m.screens.home.addvehicle

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.d2m.R
import com.example.d2m.databinding.FragmentAddCarBinding

class AddCarFragment : Fragment() {

    private lateinit var addCarBinding: FragmentAddCarBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        addCarBinding = FragmentAddCarBinding.inflate(inflater)
        return addCarBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addCarBinding.addCar.setOnClickListener {
            /*if (addCarBinding.vehicleNumber.text.isNotEmpty()) {
                addData()
                findNavController().navigate(R.id.action_addCarFragment_to_addCarBrandFragment)
            }*/

            addData()
            findNavController().navigate(R.id.action_addCarFragment_to_addCarBrandFragment)
        }

        addCarBinding.vehicleNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                addCarBinding.addCar.isEnabled = false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when (count) {
                    2 -> s.toString().plus("-")
                    4 -> s.toString().plus("-")
                    6 -> s.toString().plus("-")
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length == 13) {
                    addCarBinding.addCar.isEnabled = true
                }
            }
        })
    }

    private fun addData() {
        val addCarViewModel = ViewModelProvider(requireActivity())[AddCarViewModel::class.java]
        addCarViewModel.vehicleNumber.value = addCarBinding.vehicleNumber.text.toString()
    }
}