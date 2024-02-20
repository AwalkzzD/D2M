package com.example.d2m.screens.home.addvehicle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.d2m.R
import com.example.d2m.databinding.FragmentAddCarBinding

class AddCarFragment : Fragment() {

    private lateinit var addCarBinding: FragmentAddCarBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addCarBinding = FragmentAddCarBinding.inflate(inflater)
        return addCarBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addCarBinding.addCar.setOnClickListener {
            findNavController().navigate(R.id.action_addCarFragment_to_addMakeModelFragment)
        }
    }
}