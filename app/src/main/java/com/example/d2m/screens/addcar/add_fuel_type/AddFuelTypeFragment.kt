package com.example.d2m.screens.addcar.add_fuel_type

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.models.car.FuelType
import com.example.d2m.databinding.FragmentAddFuelTypeBinding
import com.example.d2m.screens.addcar.AddCarViewModel
import com.example.d2m.screens.utils.GenericDataAdapter

class AddFuelTypeFragment : Fragment() {

    private lateinit var addFuelTypeBinding: FragmentAddFuelTypeBinding
    private val fuelTypeList: ArrayList<FuelType> = arrayListOf()
    private lateinit var fuelTypeAdapter: GenericDataAdapter<FuelType>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        addFuelTypeBinding = FragmentAddFuelTypeBinding.inflate(inflater)
        return addFuelTypeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addCarViewModel = ViewModelProvider(requireActivity())[AddCarViewModel::class.java]
        val addFuelTypeViewModel =
            ViewModelProvider(requireActivity())[AddFuelTypeViewModel::class.java]

        addFuelTypeBinding.selectedBrandModel.text = getString(
            R.string.selected_brand_model_text,
            addCarViewModel.carBrand.value?.carBrandName ?: "null",
            addCarViewModel.carModel.value?.carModelName ?: "null"
        )

        for (i in 0..50) {
            fuelTypeList.add(
                i, FuelType(
                    "$i"
                )
            )
        }

        addFuelTypeViewModel.postLiveData(fuelTypeList)

        fuelTypeAdapter = GenericDataAdapter(
            requireActivity(),
            R.layout.fuel_list_item,
            { fuelType: FuelType ->
                addCarViewModel.fuelType.value = fuelType
            }) { item, itemView ->

            val fuelName = itemView.findViewById<TextView>(R.id.fuelName)
            with(item) {
                fuelName.text = "Fuel Type ${this.fuelName}"
            }
        }
        fuelTypeAdapter.addData(fuelTypeList)

        addFuelTypeBinding.fuelTypeRV.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = fuelTypeAdapter
        }

        addCarViewModel.isDefault.value = "0"
        addFuelTypeBinding.setAsDefaultCar.setOnCheckedChangeListener { _, isChecked ->
            when {
                isChecked -> {
                    addCarViewModel.isDefault.value = "1"
                }

                !isChecked -> {
                    addCarViewModel.isDefault.value = "0"
                }
            }
        }

        addFuelTypeBinding.addCar.setOnClickListener {
            val sharedPreferences = activity?.getSharedPreferences("userData", MODE_PRIVATE)

            addCarViewModel.apply {
                userID.value = sharedPreferences?.getString("userID", "")
                token.value = sharedPreferences?.getString("token", "")
                addCar()
            }
        }
    }
}