package com.example.d2m.screens.addcar.add_fuel_type

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.local.car.FuelType
import com.example.d2m.databinding.FragmentAddFuelTypeBinding
import com.example.d2m.screens.addcar.AddCarViewModel
import com.example.d2m.screens.home.HomeActivity
import com.example.d2m.screens.utils.GenericDataAdapter

private const val TAG = "AddFuelTypeFragment"

class AddFuelTypeFragment : Fragment() {

    private lateinit var addFuelTypeBinding: FragmentAddFuelTypeBinding
    private lateinit var fuelTypeAdapter: GenericDataAdapter<FuelType>

    private val addFuelTypeViewModel: AddFuelTypeViewModel by activityViewModels()
    private val addCarViewModel: AddCarViewModel by activityViewModels()

    private val fuelTypeList: ArrayList<FuelType> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        addFuelTypeBinding = FragmentAddFuelTypeBinding.inflate(inflater)
        return addFuelTypeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRecyclerView()

        addFuelTypeBinding.selectedBrandModel.text = getString(
            R.string.selected_brand_model_text,
            addCarViewModel.carBrand.value?.carBrandName ?: "null",
            addCarViewModel.carModel.value?.carModelName ?: "null"
        )

        addFuelTypeBinding.fuelTypeRv.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = fuelTypeAdapter
        }

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

        addCarViewModel.carAdded.observe(viewLifecycleOwner) {
            if (it.success) {
                startActivity(Intent(activity, HomeActivity::class.java))
            }
        }
    }

    private fun initRecyclerView() {
        fuelTypeAdapter = GenericDataAdapter(
            fuelTypeList,
            R.layout.fuel_list_item
        ) { fuelType: FuelType ->
            addCarViewModel.fuelType.value = fuelType
        }
    }

    private fun initViewModel() {
        for (i in 0..50) {
            fuelTypeList.add(
                i, FuelType(
                    "$i"
                )
            )
        }
        addFuelTypeViewModel.postLiveData(fuelTypeList)
        addCarViewModel.isDefault.value = "0"
    }

}