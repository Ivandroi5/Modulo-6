package com.example.alkeapi.data.network.response

data class TransactionResponse(
    val data: MutableList<TransactionDataResponse>,
    val nextPage: String?,
    val previousPage: String?
)