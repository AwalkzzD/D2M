package com.example.d2m.screens.home.orders

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.d2m.R
import com.example.d2m.data.local.orders.DataX
import com.example.d2m.data.remote.otp.verify.VerifyOtpResponseData
import com.example.d2m.databinding.FragmentOrdersBinding
import com.example.d2m.screens.utils.adapters.GenericDataAdapter
import com.example.d2m.screens.utils.base_classes.BaseActivity
import com.example.d2m.screens.utils.base_classes.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson

private const val TAG = "OrdersFragment"

class OrdersFragment : BaseFragment<FragmentOrdersBinding, OrdersViewModel>(
    R.layout.fragment_orders, OrdersViewModel::class.java
) {

    private lateinit var ordersAdapter: GenericDataAdapter<DataX>
    private lateinit var ordersList: MutableList<DataX>

    override fun setUpView() {
        setUpToolbar()
        initViewModel()
        initViews()
        initRecyclerView()
    }

    private fun initViewModel() {
        val sharedPrefs = activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)
        val userData = Gson().fromJson(
            sharedPrefs?.getString("verifiedUser", ""), VerifyOtpResponseData::class.java
        )
        fragmentViewModel.requestUserOrders(userData.id.toString(), userData.token)

        ordersList = mutableListOf()
        fragmentViewModel.ordersLiveData.observe(viewLifecycleOwner) {
            ordersList.clear()
            ordersList.addAll(it)
            ordersAdapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        ordersAdapter = GenericDataAdapter(
            filterList(1), R.layout.order_list_item
        ) {
            // TODO: Redirect to Full Order Details Page
        }

        fragmentBinding.ordersRv.layoutManager = LinearLayoutManager(requireActivity())
        fragmentBinding.ordersRv.adapter = ordersAdapter

        ordersAdapter.notifyDataSetChanged()
    }

    private fun setUpToolbar() {
        (activity as BaseActivity<*, *>).setupToolbar(
            fragmentBinding.appBar.toolbar, "My Orders", false
        )
    }

    private fun initViews() {
        fragmentBinding.orderStatus.getTabAt(1)?.select()
        fragmentBinding.orderStatus.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        ordersAdapter.filterList(filterList(0))
                    }

                    1 -> {
                        ordersAdapter.filterList(filterList(1))
                    }

                    2 -> {
                        ordersAdapter.filterList(filterList(2))
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun filterList(orderType: Int): List<DataX> {

        if (orderType == 0) {
            return ordersList.filter {
                it.get_status_name.id == 1
            }
        }

        if (orderType == 1) {
            return ordersList.filter {
                it.get_status_name.id in 2..6
            }
        }

        if (orderType == 2) {
            return ordersList.filter {
                it.get_status_name.id in 7..8
            }
        }

        return ordersList
    }
}