package com.example.d2m.data.services.api

import com.example.d2m.data.models.home.UserHome
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