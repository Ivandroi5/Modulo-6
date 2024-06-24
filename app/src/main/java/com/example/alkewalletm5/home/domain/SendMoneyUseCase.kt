package com.example.alkewalletm5.home.domain

import com.example.alkeapi.data.network.response.AccountDataResponse
import com.example.alkeapi.data.network.response.TransactionDataResponse
import com.example.alkeapi.data.network.response.TransactionPost
import com.example.alkewalletm5.data.local.model.User
import com.example.alkewalletm5.data.network.repository.WalletRepositoryImplement
import com.example.alkewalletm5.data.network.response.UserDataResponse

class SendMoneyUseCase (private val walletRepository: WalletRepositoryImplement) {

    suspend fun myInfo(): UserDataResponse {
        return walletRepository.myProfile()
    }
    suspend fun myAccount(): MutableList<AccountDataResponse> {
        return walletRepository.myAccount()
    }

    suspend fun getAllUsers(): MutableList<User> {
        return walletRepository.getAllUsers()
    }
    suspend fun getAllAccounts(): MutableList<AccountDataResponse> {
        return walletRepository.getAllAccounts()
    }
    suspend fun createTransaction(transaction: TransactionPost): Boolean {
        return walletRepository.createTransaction(transaction)
    }
}