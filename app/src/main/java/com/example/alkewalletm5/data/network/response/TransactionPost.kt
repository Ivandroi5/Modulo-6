package com.example.alkeapi.data.network.response

data class TransactionPost(
    val amount: Int,
    val concept: String,
    val date: String,
    val type: String,
    val accountId: Int,
    val userId: Int,
    val to_account_id: Int
)