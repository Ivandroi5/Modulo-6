package com.example.alkeapi.data.network.response

data class AccountResponse(
    val previousPage: String?,
    val nextPage: String?,
    val data: MutableList<AccountDataResponse>
) {

}