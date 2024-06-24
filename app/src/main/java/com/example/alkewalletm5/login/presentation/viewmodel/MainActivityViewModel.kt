package com.example.alkewalletm5.login.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
/**
 * Uso de viewModel para manejar la futura navegación entre pantallas havía la acitvidad de intro
 */

class MainActivityViewModel : ViewModel() {

    private val _navigateToIntroAccount = MutableLiveData<Boolean>()
    val navigateToIntroAccount: LiveData<Boolean>
        get() = _navigateToIntroAccount

    init {
        _navigateToIntroAccount.value = false
    }

    fun navigateToIntroAccount() {
        _navigateToIntroAccount.value = true
    }

    fun onIntroAccountNavigated() {
        _navigateToIntroAccount.value = false
    }
}

