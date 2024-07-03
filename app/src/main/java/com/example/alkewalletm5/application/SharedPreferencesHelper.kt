package com.example.alkewalletm5.application

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * Objeto responsable de manejar las operaciones de SharedPreferences relacionadas con tokens y datos de usuario.
 */
object SharedPreferencesHelper {

    // Nombre del archivo de SharedPreferences y claves para los datos almacenados
    private const val PREFS_NAME = "ALKEWALLET_PREF"
    private const val TOKEN_KEY = "ALKE_TOKEN"
    private const val ID_USER_KEY = "ID_USER"

    /**
     * Método privado para obtener una instancia compartida de SharedPreferences.
     * @param context Contexto de la aplicación.
     * @return Instancia de SharedPreferences.
     */
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, 0)
    }

    /**
     * Guarda el token de autenticación en SharedPreferences.
     * @param context Contexto de la aplicación.
     * @param token Token de autenticación a guardar.
     */
    fun saveToken(context: Context, token: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    /**
     * Obtiene el token de autenticación guardado en SharedPreferences.
     * @param context Contexto de la aplicación.
     * @return Token de autenticación guardado o null si no hay ninguno.
     */
    fun getToken(context: Context): String? {
        return getSharedPreferences(context).getString(TOKEN_KEY, null)
    }

    /**
     * Elimina el token de autenticación de SharedPreferences.
     * @param context Contexto de la aplicación.
     */
    fun clearToken(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.remove(TOKEN_KEY)
        editor.apply()
    }

    /**
     * Elimina los datos del usuario almacenados en SharedPreferences.
     * @param context Contexto de la aplicación.
     */
    fun clearUserData(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.remove(ID_USER_KEY)
        editor.apply()
    }

    /**
     * Guarda el ID del usuario que ha iniciado sesión en SharedPreferences.
     * @param context Contexto de la aplicación.
     * @param idUser ID del usuario a guardar.
     */
    fun idUserLogged(context: Context, idUser: Int) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(ID_USER_KEY, idUser.toString())
        editor.apply()
    }
}