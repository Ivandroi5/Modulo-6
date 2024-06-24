package com.example.alkewalletm5.login.domain

import com.example.alkewalletm5.data.network.response.UserDataResponse
import com.example.alkewalletm5.data.network.repository.WalletRepositoryImplement

/**
 * Clase conservada para un futuro de uso
 */
class LoginUseCase(private val alkeRepository: WalletRepositoryImplement) {
    suspend fun login(email: String, password: String): String {
        return alkeRepository.login(email, password)
    }
    suspend fun myProfile(): UserDataResponse {
        return alkeRepository.myProfile()
    }
}