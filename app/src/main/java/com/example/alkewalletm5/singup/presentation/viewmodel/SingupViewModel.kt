package com.example.alkewalletm5.singup.presentation.viewmodel

import com.example.alkewalletm5.data.local.model.LoginUser
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkeapi.data.network.response.AccountPost
import com.example.alkeapi.data.network.response.UserPost
import com.example.alkewalletm5.R
import com.example.alkewalletm5.singup.domain.SingupUserCase
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de registro quue navega hacia la pantalla de acceso
 */
class SingupViewModel(private val singupUseCase: SingupUserCase) : ViewModel() {
    private val _navigateToLoginPage = MutableLiveData<Boolean>()
    val navigateToLoginPage: LiveData<Boolean>
        get() = _navigateToLoginPage

    private val _createAccount = MutableLiveData<Boolean>()
    val createAccount: LiveData<Boolean>
        get() = _createAccount

    init {
        _navigateToLoginPage.value = false
        _createAccount.value = false
    }

    fun navigateToLoginPage() {
        _navigateToLoginPage.value = true
    }

    fun onLoginPageNavigated() {
        _navigateToLoginPage.value = false
    }

    private val _createAccountResult = MutableLiveData<Boolean>()
    val createAccountResult: LiveData<Boolean> get() = _createAccountResult

    private val _createUserResult = MutableLiveData<Boolean>()
    val createUserResult: LiveData<Boolean> get() = _createUserResult

    /**
     * Función para traer los daotos escuchados en los campos de registro y asignarle variables nuevas
     * y finalmente agregarlis a la lista de usuarios
     * @param userId es el id del usuario creado para facilitar su movimiento en la app
     * @param balance es el saldo inicial del usuario
     * @param imageProfile es la imagen de perfil por defecto
     */
    fun createAccount(name: String, lastName: String, email: String, password: String): LoginUser {

         val newUser = LoginUser(userId = 0, name = name, lastName = lastName, email = email, password = password,
             balance =100.00, imageProfile = R.drawable.default_profile_image)
        LoginUser.dataLoginUsers.add(newUser)

        // Después de crear la cuenta, navega a la página de inicio de sesión
        _createAccount.value = true
        _navigateToLoginPage.value = true
        return newUser
    }
    fun createAccount(account: AccountPost) {
        viewModelScope.launch {
            val result = singupUseCase.createAccount(account)
            _createAccountResult.value = result
        }
    }

    fun createUser(user: UserPost) {
        viewModelScope.launch {
            val result = singupUseCase.createUser(user)
            _createUserResult.value = result
        }
    }
}