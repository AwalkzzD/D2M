package com.example.d2m.screens.home.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.d2m.data.models.home.UserHome
import com.example.d2m.data.services.api.ApiClient
import com.example.d2m.data.services.api.RequestUserData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    var userLiveData: MutableLiveData<UserHome> = MutableLiveData<UserHome>()

    fun requestUserData(userId: String) {
        val reqBody: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("user_id", userId)
            .build()

        val retrofitInstance =
            ApiClient.createService(RequestUserData::class.java) as RequestUserData
        val retrofitData = retrofitInstance.requestUserData(reqBody)

        retrofitData.enqueue(object : Callback<UserHome?> {
            override fun onResponse(call: Call<UserHome?>, response: Response<UserHome?>) {
                userLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<UserHome?>, t: Throwable) {
                Log.d("TAG", "onFailure: Failed to get User Data --> $t")
            }
        })
        ApiClient.destroyInstance()
    }
}