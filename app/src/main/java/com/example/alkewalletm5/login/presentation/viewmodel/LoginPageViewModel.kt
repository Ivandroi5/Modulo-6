package com.example.alkewalletm5.login.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkewalletm5.application.SharedPreferencesHelper
import com.example.alkewalletm5.data.local.model.LoginUser
import com.example.alkewalletm5.login.domain.LoginUseCase
import kotlinx.coroutines.launch

/**
 * Uso de viewModel para manejar la futura navegación entre pantallas hacia la página de registro y
 * el Home
 */

class LoginPageViewModel(private val loginUseCase: LoginUseCase, private val context: Context): ViewModel() {
    private val _navigateToSingUpPage = MutableLiveData<Boolean>()

    private val _navigateToHome = MutableLiveData<Boolean>()

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> = _loginResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _userProfileSaved = MutableLiveData<Boolean>()
    val userProfileSaved: LiveData<Boolean> = _userProfileSaved
    val navigateToSingUpPage: LiveData<Boolean>
        get() = _navigateToSingUpPage

    init {
        _navigateToSingUpPage.value = false
    }

    fun navigateToSingUpPage() {
        _navigateToSingUpPage.value = true
    }
    fun onSingUpPageNavigated() {
        _navigateToSingUpPage.value = false

    }
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    init {
        _navigateToHome.value = false
    }

    fun navigateToHome() {
        _navigateToHome.value = true
    }

    fun onHomeNavigated() {
        _navigateToHome.value = false
    }

    /**
     * Función para validar al usuario con la data de usuarios en la data class
     */
    fun validateCredentials(email: String, password: String): Boolean {
        val registeredUsers = LoginUser.dataLoginUsers
        val user = registeredUsers.find { it.email == email && it.password == password }
        return user != null
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val token = loginUseCase.login(email, password)
                SharedPreferencesHelper.saveToken(context, token)
                _loginResult.value = token
                searchAndSaveUserProfile()
            } catch (e: Exception) {
                _error.value = e.message

            }
        }
    }

    private fun searchAndSaveUserProfile() {
        viewModelScope.launch {
            try {
                val user = loginUseCase.myProfile()
                SharedPreferencesHelper.idUserLogged(context, user.id)
                _userProfileSaved.value = true
            } catch (e: Exception) {
                _error.value = e.message
                _userProfileSaved.value = false

            }
        }
    }
}