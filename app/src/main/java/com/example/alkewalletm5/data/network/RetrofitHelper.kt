package com.example.alkewalletm5.data.network

import android.content.Context
import com.example.alkewalletm5.application.AppConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(context: Context): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}