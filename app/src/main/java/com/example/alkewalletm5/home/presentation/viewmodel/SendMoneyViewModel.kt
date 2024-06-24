package com.example.alkewalletm5.home.presentation.viewmodel

import androidx.lifecycle.*
import com.example.alkeapi.data.network.response.AccountDataResponse
import com.example.alkeapi.data.network.response.TransactionPost
import com.example.alkewalletm5.data.local.model.Transaction
import com.example.alkewalletm5.data.local.model.User
import com.example.alkewalletm5.data.network.response.UserDataResponse
import com.example.alkewalletm5.home.domain.DbUseCase
import com.example.alkewalletm5.home.domain.SendMoneyUseCase
import kotlinx.coroutines.launch

class SendMoneyViewModel(
    private val sendMoneyUseCase: SendMoneyUseCase,
    private val databaseUseCase: DbUseCase
) : ViewModel() {

    private val _user = MutableLiveData<UserDataResponse>()
    val user: LiveData<UserDataResponse> get() = _user

    private val _account = MutableLiveData<AccountDataResponse>()
    val account: LiveData<AccountDataResponse> get() = _account

    private val _error = MutableLiveData<String>()


    private val _usersResult = MutableLiveData<MutableList<User>>()
    val usersResult: LiveData<MutableList<User>> get() = _usersResult

    private val _accountResult = MutableLiveData<MutableList<AccountDataResponse>>()
    val accountResult: LiveData<MutableList<AccountDataResponse>> get() = _accountResult

    private val _transactionResult = MutableLiveData<Boolean>()
    val transactionResult: LiveData<Boolean> get() = _transactionResult


    init {
        myProfile()
        myAccount()
        getAllUsers()
        getAllAccounts()
    }

    fun myProfile() {
        viewModelScope.launch {
            try {
                val user = sendMoneyUseCase.myInfo()
                _user.value = user
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun myAccount() {
        viewModelScope.launch {
            try {
                val account = sendMoneyUseCase.myAccount()
                _account.value = account[0]
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun getAllUsers() {
        viewModelScope.launch {
            try {
                val users = sendMoneyUseCase.getAllUsers()
                _usersResult.value = users
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }


    fun getAllAccounts() {
        viewModelScope.launch {
            try {
                val allAccounts = sendMoneyUseCase.getAllAccounts()
                val uniqueUserAccounts = allAccounts.groupBy { it.userId }.map { it.value.first() }
                _accountResult.value = uniqueUserAccounts.toMutableList()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
    fun createTransaction(transaction: TransactionPost) {
        viewModelScope.launch {
            try {
                val result = sendMoneyUseCase.createTransaction(transaction)
                _transactionResult.value = result
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }


    fun insertTransaction( transaction: Transaction){
        viewModelScope.launch {
            databaseUseCase.insertTransaction(transaction)
        }
    }

}