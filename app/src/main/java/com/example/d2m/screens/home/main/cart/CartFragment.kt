package com.example.d2m.screens.home.main.cart

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.d2m.R
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.databinding.FragmentCartBinding
import com.example.d2m.screens.home.main.checkout.CheckoutViewModel
import com.example.d2m.screens.utils.base_classes.BaseActivity
import com.example.d2m.screens.utils.base_classes.BaseFragment
import com.example.d2m.screens.utils.adapters.GenericDataAdapter

private const val TAG = "CartFragment"

class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>(
    R.layout.fragment_cart, CartViewModel::class.java
) {

    private lateinit var cartServiceXAdapter: GenericDataAdapter<ServiceX>

    private var cartServiceXList: MutableList<ServiceX> = mutableListOf()

    private val checkoutViewModel: CheckoutViewModel by activityViewModels()

    override fun setUpView() {
        setUpToolBar()
        initViewModel()
        initRecyclerView()
        seUpListeners()
    }

    private fun setUpToolBar() {
        (activity as BaseActivity<*, *>).setupToolbar(
            fragmentBinding.appBar.toolbar, "My Cart", true
        )
    }

    private fun initViewModel() {

        fragmentViewModel.addedCartServices.postValue(checkoutViewModel.addedCartServices.value)
        Log.d(TAG, "initViewModel: ${fragmentViewModel.addedCartServices.value}")

        fragmentViewModel.addedCartServices.observe(viewLifecycleOwner) {
            cartServiceXList.clear()
            cartServiceXList.addAll(it)
            cartServiceXAdapter.notifyDataSetChanged()

            updateCartAmount(it)
        }

        fragmentBinding.cart = fragmentViewModel
    }

    private fun updateCartAmount(addedCartServices: ArrayList<ServiceX>) {
        var cartTotal = 0.0
        for (serviceX in addedCartServices) {
            cartTotal += serviceX.price
        }

        fragmentBinding.totalCartAmount.text = buildString {
            append("₹")
            append(cartTotal)
        }

        fragmentBinding.cartOrderBottom.cartTotal.text = buildString {
            append("₹ ")
            append(cartTotal)
        }

    }

    private fun initRecyclerView() {
        cartServiceXAdapter = GenericDataAdapter(
            cartServiceXList, R.layout.cart_list_item
        ) { serviceX: ServiceX ->
            showToast(serviceX.serviceTitle, Toast.LENGTH_SHORT)
        }

        cartServiceXAdapter.setVM(fragmentViewModel)

        fragmentBinding.cartItemsRv.apply {
            adapter = cartServiceXAdapter
        }
    }

    private fun seUpListeners() {
        fragmentBinding.cartOrderBottom.payNowButton.setOnClickListener {
            showToast("Redirecting to Payment Gateway", Toast.LENGTH_LONG)
        }
    }
}
