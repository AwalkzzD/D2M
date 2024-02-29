package com.example.d2m.screens.home.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.models.home.Banner
import com.example.d2m.data.models.home.Service
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
    private var bannerList: MutableList<Banner> = mutableListOf()
    private var servicesList: MutableList<Service> = mutableListOf()

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

        homeBinding.featuresRV.apply {
            adapter = bannerAdapter
        }

        homeBinding.servicesRV.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = serviceAdapter
        }

    }

    private fun initRecyclerView() {
        bannerAdapter = GenericDataAdapter(
            bannerList,
            R.layout.feature_list_item
        ) { banner: Banner ->
            Toast.makeText(
                requireActivity(), banner.banner_name, Toast.LENGTH_SHORT
            ).show()
        }

        serviceAdapter =
            GenericDataAdapter(servicesList, R.layout.services_list_item) { service: Service ->
                findNavController().navigate(R.id.action_homeFragment_to_serviceFragment)
                serviceViewModel.serviceXLiveData.postValue(service.services)
            }

    }

    private fun initViewModel() {
        homeActivityViewModel.userLiveData.observe(viewLifecycleOwner) {
            homeViewModel.bannerLiveData.value = it.data.banners
            homeViewModel.serviceLiveData.value = it.data.services
        }

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner) {
            bannerList.clear()
            bannerList.addAll(it)
            bannerAdapter.notifyDataSetChanged()
        }

        homeViewModel.serviceLiveData.observe(viewLifecycleOwner) {
            servicesList.clear()
            servicesList.addAll(it)
            serviceAdapter.notifyDataSetChanged()
        }
    }
}