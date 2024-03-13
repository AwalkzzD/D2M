package com.example.d2m.screens.home.main.home

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.local.car.CarBrand
import com.example.d2m.data.local.home.Banner
import com.example.d2m.data.local.home.Service
import com.example.d2m.databinding.FragmentHomeBinding
import com.example.d2m.screens.home.HomeActivityViewModel
import com.example.d2m.screens.home.main.service.ServiceViewModel
import com.example.d2m.screens.utils.BaseFragment
import com.example.d2m.screens.utils.GenericDataAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home,
    HomeViewModel::class.java
) {

    private lateinit var bannerAdapter: GenericDataAdapter<Banner>
    private lateinit var serviceAdapter: GenericDataAdapter<Service>

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val homeActivityViewModel: HomeActivityViewModel by activityViewModels()
    private val serviceViewModel: ServiceViewModel by activityViewModels()

    private val bannerList: MutableList<Banner> = mutableListOf()
    private val servicesList: MutableList<Service> = mutableListOf()

    override fun onCreateSetup() {
        super.onCreateSetup()
        setupActionBar()
    }

    override fun setUpView() {
        initViewModel()
        initRecyclerView()
        setupListeners()
    }

    private fun setupListeners() {
        fragmentBinding.serviceSearchView.searchItem.apply {
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
        /**
         * features/banners adapter & recycler view setup
         * */
        bannerAdapter = GenericDataAdapter(
            bannerList, R.layout.feature_list_item
        ) { banner: Banner ->
            Toast.makeText(
                requireActivity(), banner.bannerName, Toast.LENGTH_SHORT
            ).show()
        }

        fragmentBinding.featuresRv.apply {
            adapter = bannerAdapter
        }

        /**
         * services adapter & recycler view setup
         * */
        serviceAdapter = GenericDataAdapter(
            servicesList, R.layout.services_list_item
        ) { service: Service ->
            findNavController().navigate(R.id.action_homeFragment_to_serviceFragment)
            serviceViewModel.serviceXLiveData.postValue(service.services)
        }

        fragmentBinding.servicesRv.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = serviceAdapter
        }

    }

    private fun initViewModel() {
        homeActivityViewModel.userLiveData.observe(viewLifecycleOwner) {
            fragmentViewModel.bannerLiveData.value = it.userData?.banners
            fragmentViewModel.serviceLiveData.value = it.userData?.services
        }

        fragmentViewModel.bannerLiveData.observe(viewLifecycleOwner) {
            bannerList.clear()
            bannerList.addAll(it)
            bannerAdapter.notifyItemRangeChanged(0, it.size)
        }

        fragmentViewModel.serviceLiveData.observe(viewLifecycleOwner) {
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
            fragmentBinding.servicesRv.visibility = View.GONE
            fragmentBinding.result.noResultFound.visibility = View.VISIBLE
        } else {
            fragmentBinding.servicesRv.visibility = View.VISIBLE
            fragmentBinding.result.noResultFound.visibility = View.GONE
            serviceAdapter.filterList(filteredList)
        }

    }

    private fun setupActionBar() {
        navController = findNavController()
        appBarConfiguration = AppBarConfiguration(navController.graph)
        fragmentBinding.bottomNav.setupWithNavController(navController)
    }
}