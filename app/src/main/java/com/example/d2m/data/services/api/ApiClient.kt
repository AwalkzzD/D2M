package com.example.d2m.data.services.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private const val BASE_URL = "http://d2m.php.dev.drcsystems.ooo/"
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