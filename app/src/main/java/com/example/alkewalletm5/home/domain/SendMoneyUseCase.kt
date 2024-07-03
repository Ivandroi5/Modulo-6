package com.example.alkewalletm5.home.domain

import com.example.alkeapi.data.network.response.AccountDataResponse
import com.example.alkeapi.data.network.response.TransactionDataResponse
import com.example.alkeapi.data.network.response.TransactionPost
import com.example.alkewalletm5.data.local.model.User
import com.example.alkewalletm5.data.network.repository.WalletRepositoryImplement
import com.example.alkewalletm5.data.network.response.UserDataResponse

/**
 * Caso de uso que encapsula la lógica relacionada con el envío de dinero y la gestión de datos de usuario y cuenta.
 * Utiliza un repositorio para obtener datos y realizar operaciones.
 */
class SendMoneyUseCase(private val walletRepository: WalletRepositoryImplement) {

    /**
     * Obtiene la información del usuario actualmente autentificado.
     * @return Objeto UserDataResponse que contiene la información del usuario.
     */
    suspend fun myInfo(): UserDataResponse {
        return walletRepository.myProfile()
    }

    /**
     * Obtiene las cuentas asociadas al usuario actual.
     * @return Lista mutable de objetos AccountDataResponse que representan las cuentas del usuario.
     */
    suspend fun myAccount(): MutableList<AccountDataResponse> {
        return walletRepository.myAccount()
    }

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     * @return Lista mutable de objetos User que representan a todos los usuarios registrados.
     */
    suspend fun getAllUsers(): MutableList<User> {
        return walletRepository.getAllUsers()
    }

    /**
     * Obtiene todas las cuentas registradas en el sistema.
     * @return Lista mutable de objetos AccountDataResponse que representan todas las cuentas registradas.
     */
    suspend fun getAllAccounts(): MutableList<AccountDataResponse> {
        return walletRepository.getAllAccounts()
    }

    /**
     * Crea una nueva transacción en el sistema.
     * @param transaction Objeto TransactionPost que contiene los detalles de la transacción a crear.
     * @return `true` si la transacción se creó correctamente, `false` en caso contrario.
     */
    suspend fun createTransaction(transaction: TransactionPost): Boolean {
        return walletRepository.createTransaction(transaction)
    }
}
