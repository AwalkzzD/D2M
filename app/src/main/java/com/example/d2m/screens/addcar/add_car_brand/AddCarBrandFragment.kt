package com.example.d2m.screens.addcar.add_car_brand

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.models.car.CarBrand
import com.example.d2m.databinding.FragmentAddCarBrandBinding
import com.example.d2m.screens.addcar.AddCarViewModel
import com.example.d2m.screens.utils.GenericDataAdapter

class AddCarBrandFragment : Fragment() {

    private lateinit var addCarBrandBinding: FragmentAddCarBrandBinding
    private val brandList: ArrayList<CarBrand> = arrayListOf()
    private lateinit var carBrandsAdapter: GenericDataAdapter<CarBrand>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        addCarBrandBinding = FragmentAddCarBrandBinding.inflate(inflater)
        return addCarBrandBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addCarViewModel = ViewModelProvider(requireActivity())[AddCarViewModel::class.java]
        val addCarBrandViewModel =
            ViewModelProvider(requireActivity())[AddCarBrandViewModel::class.java]

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

        addCarBrandViewModel.postLiveData(brandList)

        carBrandsAdapter =
            GenericDataAdapter(requireActivity(), R.layout.brand_list_item, { carBrand: CarBrand ->
                addCarViewModel.carBrand.value = carBrand
                findNavController().navigate(R.id.action_addCarBrandFragment_to_addCarModelFragment)
            }) { item, itemView ->
                val carBrandImage = itemView.findViewById<ImageView>(R.id.brandImage)
                val carBrandName = itemView.findViewById<TextView>(R.id.brandName)
                with(item) {
                    carBrandImage.setImageDrawable(this.carBrandImage)
                    carBrandName.text = this.carBrandName
                }
            }

        carBrandsAdapter.addData(brandList)

        addCarBrandBinding.carBrandRV.apply {
            layoutManager = GridLayoutManager(requireActivity(), 3)
            adapter = carBrandsAdapter
        }

        addCarBrandBinding.searchBrand.apply {
            addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?, start: Int, count: Int, after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence?, start: Int, before: Int, count: Int
                    ) {

                    }

                    override fun afterTextChanged(s: Editable?) = filter(s.toString().lowercase())
                }
            )
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
            Toast.makeText(requireActivity(), "No Data found", Toast.LENGTH_SHORT).show()
            addCarBrandBinding.carBrandRV.visibility = View.GONE
            addCarBrandBinding.noResultFound.visibility = View.VISIBLE
        } else {
            addCarBrandBinding.carBrandRV.visibility = View.VISIBLE
            addCarBrandBinding.noResultFound.visibility = View.GONE
            carBrandsAdapter.filterList(filteredList)
        }
    }

}