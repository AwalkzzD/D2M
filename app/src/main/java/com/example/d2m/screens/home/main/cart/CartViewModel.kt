package com.example.d2m.screens.home.main.cart

import androidx.lifecycle.MutableLiveData
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.screens.utils.BaseViewModel

private const val TAG = "CartViewModel"

class CartViewModel : BaseViewModel() {
    var addedCartServices: MutableLiveData<ArrayList<ServiceX>> =
        MutableLiveData<ArrayList<ServiceX>>()

    fun removeService(serviceX: ServiceX) {
        val cartList = addedCartServices.value ?: arrayListOf()
        cartList.remove(serviceX)
        addedCartServices.postValue(cartList)
    }
}