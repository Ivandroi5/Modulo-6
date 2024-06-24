package com.example.alkewalletm5.home.domain

import com.example.alkewalletm5.data.local.model.Transaction
import com.example.alkewalletm5.data.local.repository.TransactionR

class DbUseCase (private val transactionR: TransactionR) {

    suspend fun getAllTransaction() = transactionR.getAllTransactions()
    suspend fun insertTransaction(transaction: Transaction) =
        transactionR.insertTransaction(transaction)

}