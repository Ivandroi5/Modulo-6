package com.example.alkewalletm5.data.network.repository

import com.example.alkeapi.data.network.response.LoginPost
import com.example.alkewalletm5.data.local.model.User
import com.example.alkewalletm5.data.network.AlkeApiService
import com.example.alkeapi.data.network.response.AccountDataResponse
import com.example.alkeapi.data.network.response.AccountPost
import com.example.alkeapi.data.network.response.TransactionDataResponse
import com.example.alkeapi.data.network.response.TransactionPost
import com.example.alkewalletm5.data.network.response.UserDataResponse
import com.example.alkeapi.data.network.response.UserPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WalletRepositoryImplement(
    private val apiService: AlkeApiService
) : WalletRepository {


    override suspend fun login(email: String, password: String): String {
        return withContext(Dispatchers.IO) {
            val loginRequest = LoginPost(email, password)
            val response = apiService.login(loginRequest)
            response.accessToken
        }
    }

    override suspend fun myProfile(): UserDataResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.myProfile()
                response
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun myAccount(): MutableList<AccountDataResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.myAccount()
                response
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun myTransactions(): MutableList<TransactionDataResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.myTransactions()
                response.data
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun createUser(user: UserPost): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.createUser(user)
                response.isSuccessful
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun getUserById(id: Int): UserDataResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getUserById(id)
                response
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun createAccount(account: AccountPost): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.createAccount(account)
                response.isSuccessful
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun getAccountsById(id: Int): AccountDataResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAccountById(id)
                response
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun getAllUsers(): MutableList<User> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAllUsers()
                response.data
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun createTransaction(transaction: TransactionPost): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.createTransactions(transaction)
                response.isSuccessful
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun getAllAccounts(): MutableList<AccountDataResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAllAccounts()
                response.data
            } catch (e: Exception) {
                throw e
            }
        }
    }

}