package com.example.alkeapi.data.network.response

import com.example.alkewalletm5.data.local.model.User

data class UserResponse(
    val previousPage: String?,
    val nextPage: String?,
    val data: MutableList<User>
)