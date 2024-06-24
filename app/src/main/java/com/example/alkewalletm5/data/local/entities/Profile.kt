package com.example.alkewalletm5.data.local.entities

import com.example.alkewalletm5.R

/**
 * dataclass y companion object para pintar el perfil del usuario con un adaptador
 * se separó del registro de usuario para solo pintar un usuario en el home
 */
data class Profile(
    val name: String = "",
    var balance: Double = 0.0,
    var imageProfile: Int = 0
)
 {
    companion object {
        val dataProfiles = mutableListOf<Profile>(
            Profile("Arturo",
                1000.0, R.drawable.arturo
            ),

            )
        val dataProfileEmpty = mutableListOf<Profile>()
    }
}