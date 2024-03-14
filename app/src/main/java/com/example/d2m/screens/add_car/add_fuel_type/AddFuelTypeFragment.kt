package com.example.d2m.screens.add_car.add_fuel_type

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.local.car.FuelType
import com.example.d2m.databinding.FragmentAddFuelTypeBinding
import com.example.d2m.screens.add_car.AddCarViewModel
import com.example.d2m.screens.home.HomeActivity
import com.example.d2m.screens.utils.BaseActivity
import com.example.d2m.screens.utils.BaseFragment
import com.example.d2m.screens.utils.GenericDataAdapter

private const val TAG = "AddFuelTypeFragment"

class AddFuelTypeFragment : BaseFragment<FragmentAddFuelTypeBinding, AddFuelTypeViewModel>(
    R.layout.fragment_add_fuel_type, AddFuelTypeViewModel::class.java
) {

    private lateinit var fuelTypeAdapter: GenericDataAdapter<FuelType>

    private val addCarViewModel: AddCarViewModel by activityViewModels()
    private val fuelTypeList: ArrayList<FuelType> = arrayListOf()

    override fun setUpView() {
        setUpToolBar()
        initViewModel()
        initRecyclerView()
        setupListeners()
    }

    private fun setUpToolBar() {
        (activity as BaseActivity<*, *>).customiseToolbar("Select Fuel Type", true)
    }

    private fun initViewModel() {
        for (i in 0..50) {
            fuelTypeList.add(
                i, FuelType(
                    "$i"
                )
            )
        }
        fragmentViewModel.postLiveData(fuelTypeList)
        addCarViewModel.isDefault.value = "0"

        addCarViewModel.carAdded.observe(viewLifecycleOwner) {
            if (it.success) {
                startActivity(Intent(activity, HomeActivity::class.java))
            }
        }

        fragmentBinding.selectedBrandModel.text = getString(
            R.string.selected_brand_model_text,
            addCarViewModel.carBrand.value?.carBrandName ?: "null",
            addCarViewModel.carModel.value?.carModelName ?: "null"
        )
    }

    private fun initRecyclerView() {
        fuelTypeAdapter = GenericDataAdapter(
            fuelTypeList, R.layout.fuel_list_item
        ) { fuelType: FuelType ->
            addCarViewModel.fuelType.value = fuelType
        }

        fragmentBinding.fuelTypeRv.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = fuelTypeAdapter
        }
    }

    private fun setupListeners() {

        fragmentBinding.setAsDefaultCar.setOnCheckedChangeListener { _, isChecked ->
            when {
                isChecked -> {
                    addCarViewModel.isDefault.value = "1"
                }

                !isChecked -> {
                    addCarViewModel.isDefault.value = "0"
                }
            }
        }

        fragmentBinding.addCar.setOnClickListener {
            val sharedPreferences = activity?.getSharedPreferences("userData", MODE_PRIVATE)

            addCarViewModel.apply {
                userID.value = sharedPreferences?.getString("userID", "")
                token.value = sharedPreferences?.getString("token", "")
                Log.d(TAG, "setupListeners: ${userID.value} ${token.value}")
                addCar()
            }
        }

    }

}