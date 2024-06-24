package com.example.alkewalletm5.login.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Uso de viewModel para manejar la futura navegación entre pantallas
 */
class IntroActivityViewModel : ViewModel() {

    /**
     * Para ir a la página de registrar cuenta con un booleano
     */


    private val _navigateToSingUpPage = MutableLiveData<Boolean>()
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

    /**
     * Para ir a la página de acceder a cuenta con un booleano
     */
    private val _navigateToLoginPage = MutableLiveData<Boolean>()
    val navigateToLoginPage: LiveData<Boolean>
        get() = _navigateToLoginPage

    init {
        _navigateToLoginPage.value = false
    }

    fun navigateToLoginPage() {
        _navigateToLoginPage.value = true
    }

    fun onLoginPageNavigated() {
        _navigateToLoginPage.value = false
    }
}