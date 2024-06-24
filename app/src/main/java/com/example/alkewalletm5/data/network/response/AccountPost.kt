package com.example.alkeapi.data.network.response

data class AccountPost(
    val creationDate: String,
    val isBlocked: Boolean = false,
    val money: Int,
    val userId: Int
)