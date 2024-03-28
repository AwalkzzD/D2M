package com.example.d2m.screens.home.main.home

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.local.car.CarBrand
import com.example.d2m.data.local.home.Banner
import com.example.d2m.data.local.home.Service
import com.example.d2m.data.remote.otp.verify.VerifyOtpResponseData
import com.example.d2m.databinding.FragmentHomeBinding
import com.example.d2m.screens.account.ProfileViewModel
import com.example.d2m.screens.home.HomeActivityViewModel
import com.example.d2m.screens.home.main.service.ServiceViewModel
import com.example.d2m.screens.utils.BaseActivity
import com.example.d2m.screens.utils.BaseFragment
import com.example.d2m.screens.utils.GenericDataAdapter
import com.google.gson.Gson

private const val TAG = "HomeFragment"

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home, HomeViewModel::class.java
) {

    private lateinit var bannerAdapter: GenericDataAdapter<Banner>
    private lateinit var serviceAdapter: GenericDataAdapter<Service>

    private lateinit var navController: NavController

    private val serviceViewModel: ServiceViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()

    private val bannerList: MutableList<Banner> = mutableListOf()
    private val servicesList: MutableList<Service> = mutableListOf()

    override fun setUpView() {
        setUpToolBar()
        setupBottomNavBar()
        initViewModel()
        initRecyclerView()
        setupListeners()
        loadUserProfileData()
    }

    private fun setUpToolBar() {
        (activity as BaseActivity<*, *>).setupToolbar(fragmentBinding.appBar.toolbar, "", false)
    }

    private fun setupBottomNavBar() {
        navController = findNavController()
        fragmentBinding.bottomNav.setupWithNavController(navController)
    }

    private fun initViewModel() {
        (getActivityViewModel() as HomeActivityViewModel).userLiveData.observe(viewLifecycleOwner) {
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

    private fun initRecyclerView() {
        /**
         * features/banners adapter & recycler view setup
         * */
        bannerAdapter = GenericDataAdapter(
            bannerList, R.layout.feature_list_item
        ) { banner: Banner ->
            showToast(banner.bannerName, Toast.LENGTH_SHORT)
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

    private fun setupListeners() {
        fragmentBinding.serviceSearchView.searchItem.apply {
            hint = "Search Service"
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                    Unit

                override fun afterTextChanged(s: Editable?) = filter(s.toString().lowercase())
            })
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
            showToast("No Data found", Toast.LENGTH_SHORT)
            fragmentBinding.servicesRv.visibility = View.GONE
            fragmentBinding.result.noResultFound.visibility = View.VISIBLE
        } else {
            fragmentBinding.servicesRv.visibility = View.VISIBLE
            fragmentBinding.result.noResultFound.visibility = View.GONE
            serviceAdapter.filterList(filteredList)
        }

    }

    private fun loadUserProfileData() {
        val sharedPrefs = activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)
        val userData = Gson().fromJson(
            sharedPrefs?.getString("verifiedUser", ""),
            VerifyOtpResponseData::class.java
        )
        profileViewModel.loadUserProfile(
            userData.id.toString(),
            userData.token,
            userData.email,
            userData.totalCars.toString(),
            userData.fullName,
            userData.isSubscribedUser.toString(),
            userData.subscribePlanName.toString()
        )

    }

}
