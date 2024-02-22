package com.example.d2m.screens.home.addvehicle

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.models.car.CarBrand
import com.example.d2m.databinding.FragmentAddCarBrandBinding
import com.example.d2m.screens.utils.CarBrandsAdapter


class AddCarBrandFragment : Fragment() {
    private lateinit var carBrandBinding: FragmentAddCarBrandBinding
    private val brandList: ArrayList<CarBrand> = arrayListOf()
    private lateinit var carBrandsAdapter: CarBrandsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        carBrandBinding = FragmentAddCarBrandBinding.inflate(inflater)
        return carBrandBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0..100) {
            brandList.add(
                i, CarBrand(
                    "Brand $i",
                    ContextCompat.getDrawable(requireActivity(), R.drawable.ic_dummy_brand)!!
                )
            )
        }

        carBrandsAdapter = CarBrandsAdapter(brandList) { carBrand ->
            val addCarBrandViewModel =
                ViewModelProvider(requireActivity())[AddCarViewModel::class.java]
            addCarBrandViewModel.brandName.value = carBrand.carBrandName
            findNavController().navigate(R.id.action_addCarBrandFragment_to_addCarModelFragment)
        }

        carBrandBinding.carBrandRV.layoutManager = GridLayoutManager(requireActivity(), 3)
        carBrandBinding.carBrandRV.adapter = carBrandsAdapter

        carBrandBinding.searchBrand.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString().lowercase())
            }
        })
    }

    private fun filter(text: String) {
        val filteredList = ArrayList<CarBrand>()
        for (carBrand in brandList) {
            if (carBrand.carBrandName.lowercase().contains(text)) {
                filteredList.add(carBrand)
            }
        }
        if (filteredList == emptyList<CarBrand>()) {
            Toast.makeText(requireActivity(), "No Data found", Toast.LENGTH_SHORT).show()
            carBrandBinding.carBrandRV.visibility = View.GONE
            carBrandBinding.noResultFound.visibility = View.VISIBLE
        } else {
            carBrandBinding.carBrandRV.visibility = View.VISIBLE
            carBrandBinding.noResultFound.visibility = View.GONE
            carBrandsAdapter.filterList(filteredList)
        }
    }

}