package com.example.d2m.screens.addcar.add_car_model

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
import com.example.d2m.data.models.car.CarModel
import com.example.d2m.databinding.FragmentAddCarModelBinding
import com.example.d2m.screens.addcar.viewmodel.AddCarViewModel
import com.example.d2m.screens.utils.CarModelsAdapter

class AddCarModelFragment : Fragment() {

    private lateinit var addCarModelBinding: FragmentAddCarModelBinding
    private val modelList: ArrayList<CarModel> = arrayListOf()
    private lateinit var carModelsAdapter: CarModelsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addCarModelBinding = FragmentAddCarModelBinding.inflate(inflater)
        return addCarModelBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addCarModelViewModel =
            ViewModelProvider(requireActivity())[AddCarViewModel::class.java]

        addCarModelBinding.selectedBrand.text = addCarModelViewModel.brandName.value.toString()

        for (i in 0..100) {
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_car_dummy)?.let {
                CarModel(
                    "Model $i",
                    it
                )
            }?.let {
                modelList.add(
                    i, it
                )
            }
        }

        carModelsAdapter = CarModelsAdapter(modelList) { carModel ->
            addCarModelViewModel.modelName.value = carModel.carModelName
            findNavController().navigate(R.id.action_addCarModelFragment_to_addFuelTypeFragment)
        }

        addCarModelBinding.carModelRV.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = carModelsAdapter
        }

        addCarModelBinding.searchModel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) = filter(s.toString().lowercase())
        })
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
            addCarModelBinding.carModelRV.visibility = View.GONE
            addCarModelBinding.noResultFound.visibility = View.VISIBLE
        } else {
            addCarModelBinding.carModelRV.visibility = View.VISIBLE
            addCarModelBinding.noResultFound.visibility = View.GONE
            carModelsAdapter.filterList(filteredList)
        }
    }
}