package com.example.d2m.screens.add_car.add_car_model

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.local.car.CarBrand
import com.example.d2m.data.local.car.CarModel
import com.example.d2m.databinding.FragmentAddCarModelBinding
import com.example.d2m.screens.add_car.AddCarViewModel
import com.example.d2m.screens.utils.BaseFragment
import com.example.d2m.screens.utils.GenericDataAdapter

class AddCarModelFragment : BaseFragment<FragmentAddCarModelBinding, AddCarModelViewModel>(
    R.layout.fragment_add_car_model,
    AddCarModelViewModel::class.java
) {

    private lateinit var carModelsAdapter: GenericDataAdapter<CarModel>

    private val addCarViewModel: AddCarViewModel by activityViewModels()

    private val modelList: MutableList<CarModel> = mutableListOf()

    override fun setUpView() {
        initViewModel()
        initRecyclerView()
        setupListeners()

        fragmentBinding.selectedBrand.text = addCarViewModel.carBrand.value?.carBrandName
    }

    private fun setupListeners() {
        fragmentBinding.modelSearchView.searchItem.apply {
            hint = "Search Model"
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) = filter(s.toString().lowercase())
            })
        }
    }

    private fun initRecyclerView() {
        carModelsAdapter =
            GenericDataAdapter(modelList, R.layout.model_list_item) { carModel: CarModel ->
                addCarViewModel.carModel.value = carModel
                findNavController().navigate(R.id.action_addCarModelFragment_to_addFuelTypeFragment)
            }

        fragmentBinding.carModelRv.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = carModelsAdapter
        }
    }

    private fun initViewModel() {

        for (i in 0..100) {
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_car_dummy)
                ?.let { carDrawable ->
                    CarModel(
                        "Model $i", carDrawable
                    )
                }?.let { carModel ->
                    modelList.add(
                        i, carModel
                    )
                }
        }
        fragmentViewModel.postLiveData(modelList)

    }

    private fun filter(text: String) {

        val filteredList = ArrayList<CarModel>()

        for (carModel in modelList) {
            if (carModel.carModelName.lowercase().contains(text)) {
                filteredList.add(carModel)
            }
        }

        if (filteredList == emptyList<CarBrand>()) {
            Toast.makeText(requireActivity(), "No Data found", Toast.LENGTH_SHORT).show()
            fragmentBinding.carModelRv.visibility = View.GONE
            fragmentBinding.result.noResultFound.visibility = View.VISIBLE
        } else {
            fragmentBinding.carModelRv.visibility = View.VISIBLE
            fragmentBinding.result.noResultFound.visibility = View.GONE
            carModelsAdapter.filterList(filteredList)
        }

    }
}