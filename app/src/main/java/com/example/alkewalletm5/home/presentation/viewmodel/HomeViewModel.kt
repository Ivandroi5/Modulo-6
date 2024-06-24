package com.example.alkewalletm5.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkeapi.data.network.response.AccountDataResponse
import com.example.alkeapi.data.network.response.TransactionDataResponse
import com.example.alkewalletm5.data.local.model.Transaction
import com.example.alkewalletm5.data.network.response.UserDataResponse
import com.example.alkewalletm5.home.domain.DbUseCase
import com.example.alkewalletm5.home.domain.HomeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val homeUseCase: HomeUseCase,
    private val databaseUseCase: DbUseCase
) : ViewModel() {

    // LiveData para datos de la cuenta
    private val _user = MutableLiveData<UserDataResponse>()
    val user: LiveData<UserDataResponse> = _user

    private val _account = MutableLiveData<AccountDataResponse>()


    private val _error = MutableLiveData<String>()

    private val _transactions = MutableLiveData<MutableList<TransactionDataResponse>>()
    val transactions: LiveData<MutableList<TransactionDataResponse>> get() = _transactions

    private val _userAccount = MutableLiveData<AccountDataResponse>()
    val userAccount: LiveData<AccountDataResponse> = _userAccount

    private val _userById = MutableLiveData<MutableList<UserDataResponse>>()
    val userById: LiveData<MutableList<UserDataResponse>> get() = _userById

    // LiveData para transacciones de la base de datos local
    private val _transactionsDatabase = MutableLiveData<List<Transaction>>()
    val transactionsDatabase: LiveData<List<Transaction>> get() = _transactionsDatabase

    init {
        _userById.value = mutableListOf()
        loadMyProfile()
        loadMyAccount()
        loadMyTransactions()
        loadAllTransactionDatabase()
    }

    private fun handleException(exception: Exception) {
        _error.value = exception.message
    }

    // Cargar perfil del usuario
    fun loadMyProfile() {
        viewModelScope.launch {
            try {
                _user.value = homeUseCase.myInfo()
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    // Cargar cuenta del usuario
    private fun loadMyAccount() {
        viewModelScope.launch {
            try {
                val accounts = homeUseCase.myAccount()
                if (accounts.isNotEmpty()) {
                    _account.value = accounts[0]
                }
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    // Cargar transacciones del usuario
    private fun loadMyTransactions() {
        viewModelScope.launch {
            try {
                val transactions = withContext(Dispatchers.IO) {
                    homeUseCase.myTransactions()
                }
                _transactions.value = transactions

                transactions.forEach { transaction ->
                    getUserById(transaction.userId)
                }
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    // Obtener usuario por ID
    fun getUserById(id: Int) {
        viewModelScope.launch {
            try {
                val userTransaction = homeUseCase.getUserById(id)
                _userById.value?.let {
                    it.add(userTransaction)
                    _userById.postValue(it)
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    // Obtener cuenta por ID
    fun getAccountById(id: Int) {
        viewModelScope.launch {
            try {
                _userAccount.value = homeUseCase.getAccountById(id)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    // Cargar todas las transacciones de la base de datos local
    private fun loadAllTransactionDatabase() {
        viewModelScope.launch {
            try {
                _transactionsDatabase.value = databaseUseCase.getAllTransaction()
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

}
