package com.example.d2m.screens.home.main.service

import android.content.Context
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.d2m.R
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.data.remote.otp.verify.VerifyOtpResponseData
import com.example.d2m.databinding.FragmentServiceBinding
import com.example.d2m.screens.utils.BaseActivity
import com.example.d2m.screens.utils.BaseFragment
import com.example.d2m.screens.utils.GenericDataAdapter
import com.google.gson.Gson

class ServiceFragment : BaseFragment<FragmentServiceBinding, ServiceViewModel>(
    R.layout.fragment_service, ServiceViewModel::class.java
) {

    private lateinit var serviceXAdapter: GenericDataAdapter<ServiceX>
    private var serviceXList: MutableList<ServiceX> = mutableListOf()

    override fun setUpView() {
        setUpToolBar()
        initViewModel()
        initRecyclerView()
        setUpListeners()
    }

    private fun setUpToolBar() {
        (activity as BaseActivity<*, *>).setupToolbar(
            fragmentBinding.appBar.toolbar, "Service", true
        )
    }

    private fun initViewModel() {
        fragmentViewModel.serviceXLiveData.observe(viewLifecycleOwner) {
            serviceXList.clear()
            serviceXList.addAll(it)
            serviceXAdapter.notifyItemRangeChanged(0, it.size)
        }

        fragmentViewModel.isEmpty.observe(viewLifecycleOwner) {
            if (it) {
                serviceXAdapter.notifyDataSetChanged()
            }
        }


        fragmentBinding.cartBottomDialog.cart = fragmentViewModel
    }

    private fun initRecyclerView() {
        serviceXAdapter = GenericDataAdapter(
            serviceXList, R.layout.servicex_list_item
        ) { serviceX: ServiceX ->
            showToast(serviceX.serviceTitle, Toast.LENGTH_SHORT)
        }

        serviceXAdapter.setVM(fragmentViewModel)

        fragmentBinding.servicesRv.apply {
            adapter = serviceXAdapter
        }
    }

    private fun setUpListeners() {
        val sharedPreferences = activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)
        val userData = Gson().fromJson(
            sharedPreferences?.getString("verifiedUser", ""), VerifyOtpResponseData::class.java
        )
        fragmentBinding.cartBottomDialog.viewCart.setOnClickListener {
            if (userData.defaultAddress == null) {
                findNavController().navigate(R.id.action_serviceFragment_to_addressDetails)
            } else {
                findNavController().navigate(R.id.action_serviceFragment_to_checkoutFragment)
            }
        }

        fragmentBinding.cartBottomDialog.discardCardItems.setOnClickListener {
            showDialog(
                "Confirm Discard Items",
                "Are you sure you want discard all cart items?",
                "DISCARD",
                "CANCEL"
            ) { fragmentViewModel.removeAllServices() }
        }
    }
}
