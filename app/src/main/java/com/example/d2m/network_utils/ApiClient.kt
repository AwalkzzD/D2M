package com.example.d2m.network_utils

import com.example.d2m.common_utils.AppConstants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private var retrofitService: Any? = null

        fun <S> createService(serviceClass: Class<S>): Any? {
            if (retrofitService == null) {
                val retrofit: Retrofit =
                    Retrofit.Builder().baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                retrofitService = retrofit.create(serviceClass)
            }
            return retrofitService
        }

        fun destroyInstance() {
            retrofitService = null
        }
    }
}