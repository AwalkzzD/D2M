package com.example.d2m.screens.home.main.service

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.d2m.R
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.databinding.FragmentServiceBinding
import com.example.d2m.screens.utils.BaseFragment
import com.example.d2m.screens.utils.GenericDataAdapter

class ServiceFragment : BaseFragment<FragmentServiceBinding, ServiceViewModel>(
    R.layout.fragment_service,
    ServiceViewModel::class.java
) {

    private lateinit var serviceXAdapter: GenericDataAdapter<ServiceX>

    private var serviceXList: MutableList<ServiceX> = mutableListOf()

    override fun onCreateSetup() {
        super.onCreateSetup()
        setupActionBar()
    }

    override fun setUpView() {
        initViewModel()
        initRecyclerView()
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(fragmentBinding.appBar.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(true)
            setupActionBarWithNavController(
                findNavController(), AppBarConfiguration(findNavController().graph)
            )
        }
    }

    private fun initRecyclerView() {
        serviceXAdapter = GenericDataAdapter(
            serviceXList, R.layout.servicex_list_item
        ) { serviceX: ServiceX ->
            Toast.makeText(
                requireActivity(), serviceX.serviceTitle, Toast.LENGTH_SHORT
            ).show()
        }

        serviceXAdapter.setVM(fragmentViewModel)

        fragmentBinding.servicesRv.apply {
            adapter = serviceXAdapter
        }
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
}