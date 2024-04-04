package com.example.d2m.screens.home.main.address

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.d2m.R
import com.example.d2m.data.remote.otp.verify.GetCityAreaDetail
import com.example.d2m.databinding.FragmentSelectAreaBinding
import com.example.d2m.screens.utils.base_classes.BaseActivity
import com.example.d2m.screens.utils.base_classes.BaseFragment
import com.example.d2m.screens.utils.adapters.GenericDataAdapter

private const val TAG = "SelectAreaFragment"

class SelectAreaFragment : BaseFragment<FragmentSelectAreaBinding, SelectAreaViewModel>(
    R.layout.fragment_select_area, SelectAreaViewModel::class.java
) {

    private lateinit var areaListAdapterAhmedabad: GenericDataAdapter<GetCityAreaDetail>
    private lateinit var areaListAdapterGandhinagar: GenericDataAdapter<GetCityAreaDetail>

    private val areaListAhmedabad: MutableList<GetCityAreaDetail> = mutableListOf()
    private val areaListGandhinagar: MutableList<GetCityAreaDetail> = mutableListOf()

    override fun setUpView() {
        setUpToolBar()
        setUpData()
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

        fragmentBinding.cityOption.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.city_1 -> {
                    fragmentBinding.areaListRv.apply {
                        layoutManager = LinearLayoutManager(requireActivity())
                        adapter = areaListAdapterAhmedabad
                    }
                }

                R.id.city_2 -> {
                    fragmentBinding.areaListRv.apply {
                        layoutManager = LinearLayoutManager(requireActivity())
                        adapter = areaListAdapterGandhinagar
                    }
                }
            }
        }

        fragmentBinding.submitAreaButton.setOnClickListener {
            findNavController().popBackStack(R.id.addressDetailsFragment, false)
        }
    }

    private fun initRecyclerView() {
        areaListAdapterAhmedabad = GenericDataAdapter(areaListAhmedabad, R.layout.area_list_item) {}

        areaListAdapterAhmedabad.setVM(fragmentViewModel)

        areaListAdapterGandhinagar =
            GenericDataAdapter(areaListGandhinagar, R.layout.area_list_item) {}

        areaListAdapterGandhinagar.setVM(fragmentViewModel)

        fragmentBinding.areaListRv.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = areaListAdapterAhmedabad
        }

    }

    private fun setUpData() {
        areaListAhmedabad.add(GetCityAreaDetail("Dadhana", 1, 382120))
        areaListAhmedabad.add(GetCityAreaDetail("Ambli Ahmedabad", 2, 380058))
        areaListAhmedabad.add(GetCityAreaDetail("Azad Society", 3, 380015))
        areaListGandhinagar.add(GetCityAreaDetail("Kudasan", 1, 382120))
        areaListGandhinagar.add(GetCityAreaDetail("Raysan", 2, 380058))
        areaListGandhinagar.add(GetCityAreaDetail("Sargasan", 3, 380015))
    }

    private fun filter(text: String) {

        val filteredList = ArrayList<GetCityAreaDetail>()

        if (fragmentBinding.city1.isChecked) {
            for (area in areaListAhmedabad) {
                if (area.areaName.lowercase().contains(text) || area.pincode.toString().contains(text)) {
                    filteredList.add(area)
                }
            }

            if (filteredList == emptyList<GetCityAreaDetail>()) {
                showToast("No Data found", Toast.LENGTH_SHORT)
                fragmentBinding.areaListRv.visibility = View.GONE
                fragmentBinding.result.noResultFound.visibility = View.VISIBLE
            } else {
                fragmentBinding.areaListRv.visibility = View.VISIBLE
                fragmentBinding.result.noResultFound.visibility = View.GONE
                areaListAdapterAhmedabad.filterList(filteredList)
            }

        } else {
            for (area in areaListGandhinagar) {
                if (area.areaName.lowercase().contains(text) || area.pincode.toString().contains(text)) {
                    filteredList.add(area)
                }
            }

            if (filteredList == emptyList<GetCityAreaDetail>()) {
                showToast("No Data found", Toast.LENGTH_SHORT)
                fragmentBinding.areaListRv.visibility = View.GONE
                fragmentBinding.result.noResultFound.visibility = View.VISIBLE
            } else {
                fragmentBinding.areaListRv.visibility = View.VISIBLE
                fragmentBinding.result.noResultFound.visibility = View.GONE
                areaListAdapterGandhinagar.filterList(filteredList)
            }
        }

    }
}
