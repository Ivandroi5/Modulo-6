package com.example.alkewalletm5.login.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alkewalletm5.login.domain.LoginUseCase

class LoginPageViewModelFactory (private val loginUseCase: LoginUseCase, private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginPageViewModel(loginUseCase, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}