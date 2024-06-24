package com.example.alkewalletm5.data.network

import com.example.alkeapi.data.network.response.LoginPost
import com.example.alkeapi.data.network.response.AccountDataResponse
import com.example.alkeapi.data.network.response.AccountPost
import com.example.alkeapi.data.network.response.AccountResponse
import com.example.alkeapi.data.network.response.LoginResponse
import com.example.alkeapi.data.network.response.TransactionPost
import com.example.alkeapi.data.network.response.TransactionResponse
import com.example.alkewalletm5.data.network.response.UserDataResponse
import com.example.alkeapi.data.network.response.UserPost
import com.example.alkeapi.data.network.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AlkeApiService {

    /**
     * Acceso Login
     */

    @Headers("Content-type:application/json")
    @POST("auth/login")
    suspend fun login(@Body data: LoginPost): LoginResponse

    /**
     *  Traer datos de usuario
     */

    @GET("auth/me")
    suspend fun myProfile(): UserDataResponse

    /**
     *  Usuario loggeado
     */

    @GET("accounts/me")
    suspend fun myAccount(): MutableList<AccountDataResponse>

    /**
     * Usuarios
     * Crear - Listar - Ver detalle
     */

    @POST("users")
    suspend fun createUser(@Body data: UserPost): Response<Void>

    @GET("users")
    suspend fun getAllUsers(): UserResponse

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserDataResponse


    /**
     * Cuenta
     * Crear- Listar todas- Ver detalle
     */

    @POST("accounts")
    suspend fun createAccount(@Body data: AccountPost): Response<Void>

    @GET("accounts")
    suspend fun getAllAccounts(): AccountResponse

    @GET("accounts/{id}")
    suspend fun getAccountById(@Path("id") id: Int): AccountDataResponse




    /**
     * Transacciones
     * Crear- Listar todas
     */
    @POST("transactions")
    suspend fun createTransactions(@Body data: TransactionPost): Response<Void>
    @GET("transactions")
    suspend fun myTransactions(): TransactionResponse



}