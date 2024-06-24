package com.example.alkeapi.data.network.response

data class TransactionDataResponse(
    val id: Int,
    val accountId: Int,
    val amount: Int,
    val concept: String = "",
    val date: String,
    val to_account_id: Int,
    val type: String = "topup|payment",
    val userId: Int
)