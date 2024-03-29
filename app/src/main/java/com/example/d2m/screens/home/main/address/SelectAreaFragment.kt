package com.example.d2m.screens.home.main.address

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.d2m.R
import com.example.d2m.data.local.car.CarBrand
import com.example.d2m.data.remote.otp.verify.GetCityAreaDetail
import com.example.d2m.databinding.FragmentSelectAreaBinding
import com.example.d2m.screens.utils.BaseActivity
import com.example.d2m.screens.utils.BaseFragment
import com.example.d2m.screens.utils.GenericDataAdapter

class SelectAreaFragment : BaseFragment<FragmentSelectAreaBinding, SelectAreaViewModel>(
    R.layout.fragment_select_area, SelectAreaViewModel::class.java
) {

    private val addressDetailsViewModel: AddressDetailsViewModel by activityViewModels()

    private lateinit var areaListAdapter: GenericDataAdapter<GetCityAreaDetail>
    private val areaList: MutableList<GetCityAreaDetail> = mutableListOf()

    override fun setUpView() {
        setUpToolBar()
        initRecyclerView()
        setUpListeners()
    }

    private fun setUpToolBar() {
        (activity as BaseActivity<*, *>).setupToolbar(
            fragmentBinding.appBar.toolbar, "Select Area", true
        )
    }

    private fun setUpListeners() {

        fragmentBinding.areaSearchView.searchItem.apply {
            hint = "Search by Area name or Pincode"
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

        fragmentBinding.submitAreaButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initRecyclerView() {
        setUpData()

        areaListAdapter =
            GenericDataAdapter(areaList, R.layout.area_list_item) { area: GetCityAreaDetail ->
                addressDetailsViewModel.area.postValue(area.areaName + " - " + area.pincode.toString())
            }

        fragmentBinding.areaListRv.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = areaListAdapter
        }
    }

    private fun setUpData() {
        areaList.add(GetCityAreaDetail("Dadhana", 1, 382120))
        areaList.add(GetCityAreaDetail("Ambli Ahmedabad", 2, 380058))
        areaList.add(GetCityAreaDetail("Azad Society", 3, 380015))
        areaList.add(GetCityAreaDetail("Kudasan", 1, 382120))
        areaList.add(GetCityAreaDetail("Raysan", 2, 380058))
        areaList.add(GetCityAreaDetail("Sargasan", 3, 380015))
    }

    private fun filter(text: String) {

        val filteredList = ArrayList<GetCityAreaDetail>()

        for (area in areaList) {
            if (area.areaName.lowercase().contains(text) || area.pincode.toString()
                    .contains(text)
            ) {
                filteredList.add(area)
            }
        }

        if (filteredList == emptyList<CarBrand>()) {
            showToast("No Data found", Toast.LENGTH_SHORT)
            fragmentBinding.areaListRv.visibility = View.GONE
            fragmentBinding.result.noResultFound.visibility = View.VISIBLE
        } else {
            fragmentBinding.areaListRv.visibility = View.VISIBLE
            fragmentBinding.result.noResultFound.visibility = View.GONE
            areaListAdapter.filterList(filteredList)
        }
    }
}
