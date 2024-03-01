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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.local.car.CarBrand
import com.example.d2m.data.local.car.CarModel
import com.example.d2m.databinding.FragmentAddCarModelBinding
import com.example.d2m.screens.addcar.AddCarViewModel
import com.example.d2m.screens.utils.GenericDataAdapter

class AddCarModelFragment : Fragment() {

    private lateinit var addCarModelBinding: FragmentAddCarModelBinding
    private lateinit var carModelsAdapter: GenericDataAdapter<CarModel>

    private val addCarModelViewModel: AddCarModelViewModel by activityViewModels()
    private val addCarViewModel: AddCarViewModel by activityViewModels()

    private val modelList: MutableList<CarModel> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        addCarModelBinding = FragmentAddCarModelBinding.inflate(inflater)
        return addCarModelBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRecyclerView()

        addCarModelBinding.carModelRv.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = carModelsAdapter
        }

        addCarModelBinding.selectedBrand.text = addCarViewModel.carBrand.value?.carBrandName

        addCarModelBinding.modelSearchView.searchItem.apply {
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
        addCarModelViewModel.postLiveData(modelList)

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
            addCarModelBinding.carModelRv.visibility = View.GONE
            addCarModelBinding.result.noResultFound.visibility = View.VISIBLE
        } else {
            addCarModelBinding.carModelRv.visibility = View.VISIBLE
            addCarModelBinding.result.noResultFound.visibility = View.GONE
            carModelsAdapter.filterList(filteredList)
        }

    }
}