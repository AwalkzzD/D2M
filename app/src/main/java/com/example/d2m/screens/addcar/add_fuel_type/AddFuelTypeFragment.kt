package com.example.d2m.screens.addcar.add_fuel_type

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.models.car.FuelType
import com.example.d2m.databinding.FragmentAddFuelTypeBinding
import com.example.d2m.screens.addcar.viewmodel.AddCarViewModel
import com.example.d2m.screens.utils.FuelTypeAdapter

class AddFuelTypeFragment : Fragment() {

    private lateinit var addFuelTypeBinding: FragmentAddFuelTypeBinding
    private val fuelTypeList: ArrayList<FuelType> = arrayListOf()
    private lateinit var fuelTypeAdapter: FuelTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        addFuelTypeBinding = FragmentAddFuelTypeBinding.inflate(inflater)
        return addFuelTypeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addFuelTypeViewModel = ViewModelProvider(requireActivity())[AddCarViewModel::class.java]

        addFuelTypeBinding.selectedBrandModel.text =
            getString(
                R.string.selected_brand_model_text,
                addFuelTypeViewModel.brandName.value.toString(),
                addFuelTypeViewModel.modelName.value.toString()
            )

        for (i in 0..50) {
            fuelTypeList.add(
                i, FuelType(
                    "Fuel Type $i"
                )
            )
        }

        fuelTypeAdapter = FuelTypeAdapter(fuelTypeList) { fuelType ->
            addFuelTypeViewModel.fuelType.value = fuelType.fuelName
        }

        addFuelTypeBinding.fuelTypeRV.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = fuelTypeAdapter
        }

        addFuelTypeViewModel.isDefault.value = "0"
        addFuelTypeBinding.setAsDefaultCar.setOnCheckedChangeListener { _, isChecked ->
            when {
                isChecked -> {
                    addFuelTypeViewModel.isDefault.value = "1"
                }

                !isChecked -> {
                    addFuelTypeViewModel.isDefault.value = "0"
                }
            }
        }

        addFuelTypeBinding.addCar.setOnClickListener {
            val sharedPreferences =
                activity?.getSharedPreferences("userData", MODE_PRIVATE)

            addFuelTypeViewModel.apply {
                userID.value = sharedPreferences?.getString("userID", "")
                token.value = sharedPreferences?.getString("token", "")
                addCar()
            }
        }
    }
}