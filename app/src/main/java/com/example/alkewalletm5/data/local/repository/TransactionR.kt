package com.example.alkewalletm5.data.local.repository

import com.example.alkewalletm5.data.local.dao.TransactionDAO
import com.example.alkewalletm5.data.local.model.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionR(private val transactionDAO: TransactionDAO) {
    suspend fun insertTransaction(transaction: Transaction): Long {
        return withContext(Dispatchers.IO) {
            transactionDAO.insertTransaction(transaction)
        }
    }

    suspend fun getAllTransactions(): MutableList<Transaction> {
        return withContext(Dispatchers.IO) {
            transactionDAO.getAllTransactions()
        }
    }
}
