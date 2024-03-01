package com.example.d2m.network_utils.api_services

import com.example.d2m.data.local.home.UserHome
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestUserData {
    @POST("api/v1/user-home")
    fun requestUserData(
        @Body body: RequestBody,
    ): Call<UserHome>
}