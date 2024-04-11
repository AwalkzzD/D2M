package com.example.d2m.screens.add_car.add_car_brand

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
import com.example.d2m.databinding.FragmentAddCarBrandBinding
import com.example.d2m.screens.add_car.AddCarViewModel
import com.example.d2m.screens.utils.adapters.GenericDataAdapter
import com.example.d2m.screens.utils.base_classes.BaseActivity
import com.example.d2m.screens.utils.base_classes.BaseFragment

class AddCarBrandFragment : BaseFragment<FragmentAddCarBrandBinding, AddCarBrandViewModel>(
    R.layout.fragment_add_car_brand, AddCarBrandViewModel::class.java
) {

    private lateinit var carBrandsAdapter: GenericDataAdapter<CarBrand>

    private val addCarViewModel: AddCarViewModel by activityViewModels()

    private val brandList: MutableList<CarBrand> = mutableListOf()

    override fun setUpView() {
        setUpToolBar()
        initViewModel()
        initRecyclerView()
        setupListeners()
    }

    private fun setUpToolBar() {
        (activity as BaseActivity<*, *>).customiseToolbar("Select Brand", true)
    }

    private fun initViewModel() {

        for (i in 0..100) {
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_dummy_brand)?.let {
                CarBrand(
                    "Brand $i", it
                )
            }?.let {
                brandList.add(
                    i, it
                )
            }
        }
        fragmentViewModel.postLiveData(brandList)

    }

    private fun initRecyclerView() {
        carBrandsAdapter =
            GenericDataAdapter(brandList, R.layout.brand_list_item) { carBrand: CarBrand ->
                addCarViewModel.carBrand.value = carBrand
                findNavController().navigate(R.id.action_addCarBrandFragment_to_addCarModelFragment)
            }

        fragmentBinding.carBrandRv.apply {
            layoutManager = GridLayoutManager(requireActivity(), 3)
            adapter = carBrandsAdapter
        }
    }

    private fun setupListeners() {
        fragmentBinding.brandSearchView.searchItem.apply {
            hint = "Search Brand"
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) = Unit

                override fun onTextChanged(
                    s: CharSequence?, start: Int, before: Int, count: Int
                ) = Unit

                override fun afterTextChanged(s: Editable?) = filter(s.toString().lowercase())
            })
        }
    }

    private fun filter(text: String) {

        val filteredList = ArrayList<CarBrand>()

        for (carBrand in brandList) {
            if (carBrand.carBrandName.lowercase().contains(text)) {
                filteredList.add(carBrand)
            }
        }

        if (filteredList == emptyList<CarBrand>()) {
            showToast("No Data found", Toast.LENGTH_SHORT)
            fragmentBinding.carBrandRv.visibility = View.GONE
            fragmentBinding.result.noResultFound.visibility = View.VISIBLE
        } else {
            fragmentBinding.carBrandRv.visibility = View.VISIBLE
            fragmentBinding.result.noResultFound.visibility = View.GONE
            carBrandsAdapter.filterList(filteredList)

        }
    }
}