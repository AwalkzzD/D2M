package com.example.d2m.screens.home.main.service

import android.widget.Toast
import com.example.d2m.R
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.databinding.FragmentServiceBinding
import com.example.d2m.screens.utils.BaseActivity
import com.example.d2m.screens.utils.BaseFragment
import com.example.d2m.screens.utils.GenericDataAdapter

class ServiceFragment : BaseFragment<FragmentServiceBinding, ServiceViewModel>(
    R.layout.fragment_service, ServiceViewModel::class.java
) {

    private lateinit var serviceXAdapter: GenericDataAdapter<ServiceX>

    private var serviceXList: MutableList<ServiceX> = mutableListOf()

    override fun setUpView() {
        setUpToolBar()
        initViewModel()
        initRecyclerView()
    }

    private fun setUpToolBar() {
        (activity as BaseActivity<*, *>).setupToolbar(
            fragmentBinding.appBar.toolbar,
            "Service",
            true
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
}