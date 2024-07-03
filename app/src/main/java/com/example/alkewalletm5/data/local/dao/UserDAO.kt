package com.example.alkewalletm5.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.alkewalletm5.data.local.model.User

/**
 * Data Access Object (DAO) para la entidad User.
 * Define métodos para acceder y manipular datos relacionados con usuarios en la base de datos.
 */
@Dao
interface UserDAO {

    /**
     * Inserta un nuevo usuario en la base de datos.
     * Si hay un conflicto con un usuario existente (mismo ID), se reemplaza.
     * @param user El usuario a insertar.
     * @return El ID del usuario insertado.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long
}