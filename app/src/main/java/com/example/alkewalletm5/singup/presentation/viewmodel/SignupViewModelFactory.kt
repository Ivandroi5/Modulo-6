package com.example.alkewalletm5.singup.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alkewalletm5.singup.domain.SingupUserCase
import com.example.alkewalletm5.singup.presentation.ui.SingupActivity


class SignupViewModelFactory(
    private val singupUseCase: SingupUserCase,
    singupActivity: SingupActivity
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SingupViewModel(singupUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}