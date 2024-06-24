package com.example.alkewalletm5.home.domain

import com.example.alkeapi.data.network.response.AccountDataResponse
import com.example.alkeapi.data.network.response.TransactionDataResponse
import com.example.alkewalletm5.data.network.repository.WalletRepositoryImplement
import com.example.alkewalletm5.data.network.response.UserDataResponse

class HomeUseCase (private val walletRepository: WalletRepositoryImplement){
    suspend fun myInfo(): UserDataResponse {
        return walletRepository.myProfile()
    }

    suspend fun myAccount(): MutableList<AccountDataResponse> {
        return walletRepository.myAccount()
    }

    suspend fun myTransactions(): MutableList<TransactionDataResponse> {
        return walletRepository.myTransactions()
    }
    suspend fun getUserById(id: Int): UserDataResponse {
        return walletRepository.getUserById(id)
    }
    suspend fun getAccountById(id: Int): AccountDataResponse {
        return walletRepository.getAccountsById(id)
    }

}