package com.example.d2m.data.models.home

data class Service(
    val description: String,
    val id: Int,
    val image: String,
    val service_category_name: String,
    val services: List<ServiceX>
)