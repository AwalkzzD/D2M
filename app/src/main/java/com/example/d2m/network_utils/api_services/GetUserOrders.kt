package com.example.d2m.network_utils.api_services

import com.example.d2m.data.local.orders.MyOrders
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GetUserOrders {

    @POST("api/v1/user/my-order-list")
    fun getUserOrders(
        @Body body: RequestBody,
        @Header("Authorization") token: String
    ): Call<MyOrders>

}