package com.example.d2m.screens.home.orders

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.d2m.data.local.orders.DataX
import com.example.d2m.data.local.orders.MyOrders
import com.example.d2m.network_utils.ApiClient
import com.example.d2m.network_utils.api_services.GetUserOrders
import com.example.d2m.screens.utils.base_classes.BaseViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersViewModel : BaseViewModel() {
    var ordersLiveData: MutableLiveData<List<DataX>> = MutableLiveData<List<DataX>>()

    fun requestUserOrders(userId: String, token: String) {
        setLoadingState(true)

        val reqBody: RequestBody =
            MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("user_id", userId)
                .build()

        val retrofitInstance = ApiClient.createService(GetUserOrders::class.java) as GetUserOrders

        val retrofitData =
            retrofitInstance.getUserOrders(reqBody, "Bearer " + token)

        retrofitData.enqueue(object : Callback<MyOrders?> {
            override fun onResponse(call: Call<MyOrders?>, response: Response<MyOrders?>) {
                ordersLiveData.postValue(response.body()?.data?.my_orders?.data)
            }

            override fun onFailure(call: Call<MyOrders?>, t: Throwable) {
                Log.d("TAG", "onFailure: Failed to fetch user orders --> $t")
            }
        })

        ApiClient.destroyInstance()
        setLoadingState(false)

    }

}