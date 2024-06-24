package com.example.alkeapi.data.network.response

data class UserPost(
    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val roleId: Int = 1
)