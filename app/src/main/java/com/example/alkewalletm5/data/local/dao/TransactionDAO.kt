package com.example.alkewalletm5.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alkewalletm5.data.local.model.Transaction

/**
 * Data Access Object (DAO) para la entidad Transaction.
 * Define métodos para acceder y manipular datos relacionados con transacciones en la base de datos.
 */
@Dao
interface TransactionDAO {

    /**
     * Inserta una nueva transacción en la base de datos.
     * Si hay un conflicto con una transacción existente (mismo ID), se reemplaza.
     * @param transaction La transacción a insertar.
     * @return El ID de la transacción insertada.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction): Long

    /**
     * Obtiene todas las transacciones almacenadas en la base de datos.
     * @return Una lista mutable de todas las transacciones almacenadas.
     */
    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): MutableList<Transaction>
}