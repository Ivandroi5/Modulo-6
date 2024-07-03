package com.example.alkewalletm5.login.domain

import com.example.alkewalletm5.data.network.response.UserDataResponse
import com.example.alkewalletm5.data.network.repository.WalletRepositoryImplement


/**
 * Caso de uso para la funcionalidad de inicio de sesión y obtención de perfil del usuario.
 * Encapsula la lógica de negocio relacionada con estas operaciones.
 */
class LoginUseCase(private val alkeRepository: WalletRepositoryImplement) {

    /**
     * Función suspendida para realizar el inicio de sesión.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Token de autenticación generado después del inicio de sesión.
     */
    suspend fun login(email: String, password: String): String {
        return alkeRepository.login(email, password)
    }

    /**
     * Función suspendida para obtener el perfil del usuario actual.
     * @return Objeto UserDataResponse que contiene la información del perfil del usuario.
     */
    suspend fun myProfile(): UserDataResponse {
        return alkeRepository.myProfile()
    }
}