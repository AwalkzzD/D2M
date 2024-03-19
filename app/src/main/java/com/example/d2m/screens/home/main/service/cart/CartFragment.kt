package com.example.d2m.screens.home.main.service.cart

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.d2m.R
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.databinding.FragmentCartBinding
import com.example.d2m.screens.home.main.service.ServiceViewModel
import com.example.d2m.screens.utils.BaseActivity
import com.example.d2m.screens.utils.BaseFragment
import com.example.d2m.screens.utils.BaseViewModel
import com.example.d2m.screens.utils.GenericDataAdapter

class CartFragment : BaseFragment<FragmentCartBinding, BaseViewModel>(
    R.layout.fragment_cart, BaseViewModel::class.java
) {

    private lateinit var cartServiceXAdapter: GenericDataAdapter<ServiceX>

    private var cartServiceXList: MutableList<ServiceX> = mutableListOf()

    private val serviceViewModel: ServiceViewModel by activityViewModels()

    override fun setUpView() {
        setUpToolBar()
        initViewModel()
        initRecyclerView()
    }

    private fun setUpToolBar() {
        (activity as BaseActivity<*, *>).setupToolbar(
            fragmentBinding.appBar.toolbar, "My Cart", true
        )
    }

    private fun initViewModel() {/*serviceViewModel.serviceXLiveData.observe(viewLifecycleOwner) {
            cartServiceXList.clear()
            cartServiceXList.addAll(it)
            cartServiceXAdapter.notifyDataSetChanged()
        }*/

        fragmentBinding.cart = serviceViewModel
    }

    private fun initRecyclerView() {
        cartServiceXAdapter = GenericDataAdapter(
            cartServiceXList, R.layout.cart_list_item
        ) { serviceX: ServiceX ->
            showToast(serviceX.serviceTitle, Toast.LENGTH_SHORT)
        }

        cartServiceXAdapter.setVM(serviceViewModel)

        cartServiceXList.clear()
        cartServiceXList.addAll(serviceViewModel.addedServiceX)
        cartServiceXAdapter.notifyDataSetChanged()

        fragmentBinding.cartItemsRv.apply {
            adapter = cartServiceXAdapter
        }
    }
}
