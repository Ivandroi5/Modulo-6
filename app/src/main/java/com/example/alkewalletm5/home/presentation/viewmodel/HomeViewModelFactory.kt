package com.example.alkewalletm5.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alkewalletm5.home.domain.DbUseCase
import com.example.alkewalletm5.home.domain.HomeUseCase

class HomeViewModelFactory (
    private val homeUseCase: HomeUseCase,
    private val databaseUseCase: DbUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(homeUseCase, databaseUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}