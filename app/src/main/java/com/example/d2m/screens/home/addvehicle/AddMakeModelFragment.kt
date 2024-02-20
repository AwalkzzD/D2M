package com.example.d2m.screens.home.addvehicle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.models.car.brand.CarBrand
import com.example.d2m.databinding.FragmentAddMakeModelBinding
import com.example.d2m.screens.utils.CarBrandsAdapter


class AddMakeModelFragment : Fragment() {
    private lateinit var carBrandBinding: FragmentAddMakeModelBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        carBrandBinding = FragmentAddMakeModelBinding.inflate(inflater)
        return carBrandBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val brandList: MutableList<CarBrand> = mutableListOf()
        for (i in 0..20) {
            brandList.add(i, CarBrand("Brand $i", R.drawable.ic_dummy_brand))
        }

        carBrandBinding.carBrandRV.layoutManager = GridLayoutManager(requireActivity(), 3)
        carBrandBinding.carBrandRV.adapter = CarBrandsAdapter(brandList)
    }

}