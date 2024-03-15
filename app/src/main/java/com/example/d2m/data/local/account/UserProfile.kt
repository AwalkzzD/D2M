package com.example.d2m.data.local.account

data class UserProfile(
    val userID: String,
    val token: String,
    val email: String,
    val totalCars: String,
    val fullName: String,
    val isSubscribedUser: String,
    val subscribePlanName: String
)
