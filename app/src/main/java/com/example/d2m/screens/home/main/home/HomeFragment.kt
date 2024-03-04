package com.example.d2m.screens.home.main.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.data.local.car.CarBrand
import com.example.d2m.data.local.home.Banner
import com.example.d2m.data.local.home.Service
import com.example.d2m.databinding.FragmentHomeBinding
import com.example.d2m.screens.home.HomeActivityViewModel
import com.example.d2m.screens.home.main.service.ServiceViewModel
import com.example.d2m.screens.utils.GenericDataAdapter

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var bannerAdapter: GenericDataAdapter<Banner>
    private lateinit var serviceAdapter: GenericDataAdapter<Service>

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val homeActivityViewModel: HomeActivityViewModel by activityViewModels()
    private val serviceViewModel: ServiceViewModel by activityViewModels()

    private val bannerList: MutableList<Banner> = mutableListOf()
    private val servicesList: MutableList<Service> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRecyclerView()

        homeBinding.featuresRv.apply {
            adapter = bannerAdapter
        }

        homeBinding.servicesRv.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = serviceAdapter
        }

        homeBinding.serviceSearchView.searchItem.apply {
            hint = "Search Service"
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
        bannerAdapter = GenericDataAdapter(
            bannerList, com.example.d2m.R.layout.feature_list_item
        ) { banner: Banner ->
            Toast.makeText(
                requireActivity(), banner.bannerName, Toast.LENGTH_SHORT
            ).show()
        }

        serviceAdapter =
            GenericDataAdapter(
                servicesList,
                com.example.d2m.R.layout.services_list_item
            ) { service: Service ->
                findNavController().navigate(com.example.d2m.R.id.action_homeFragment_to_serviceFragment)
                serviceViewModel.serviceXLiveData.postValue(service.services)
            }

    }

    private fun initViewModel() {
        homeActivityViewModel.userLiveData.observe(viewLifecycleOwner) {
            homeViewModel.bannerLiveData.value = it.userData?.banners
            homeViewModel.serviceLiveData.value = it.userData?.services
        }

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner) {
            bannerList.clear()
            bannerList.addAll(it)
            bannerAdapter.notifyItemRangeChanged(0, it.size)
        }

        homeViewModel.serviceLiveData.observe(viewLifecycleOwner) {
            servicesList.clear()
            servicesList.addAll(it)
            serviceAdapter.notifyItemRangeChanged(0, it.size)
        }
    }

    private fun filter(text: String) {

        val filteredList = ArrayList<Service>()

        for (service in servicesList) {
            if (service.serviceCategoryName.lowercase().contains(text)) {
                filteredList.add(service)
            }
        }

        if (filteredList == emptyList<CarBrand>()) {
            Toast.makeText(requireActivity(), "No Data found", Toast.LENGTH_SHORT).show()
            homeBinding.servicesRv.visibility = View.GONE
            homeBinding.result.noResultFound.visibility = View.VISIBLE
        } else {
            homeBinding.servicesRv.visibility = View.VISIBLE
            homeBinding.result.noResultFound.visibility = View.GONE
            serviceAdapter.filterList(filteredList)
        }

    }
}