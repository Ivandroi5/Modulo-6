package com.example.alkewalletm5.singup.domain

import com.example.alkeapi.data.network.response.AccountPost
import com.example.alkeapi.data.network.response.UserPost
import com.example.alkewalletm5.data.network.repository.WalletRepositoryImplement

class SingupUserCase (private val walletRepository: WalletRepositoryImplement){
    suspend fun createUser(user: UserPost): Boolean {
        return walletRepository.createUser(user)
    }

    suspend fun createAccount(account: AccountPost): Boolean {
        return walletRepository.createAccount(account)
    }
}