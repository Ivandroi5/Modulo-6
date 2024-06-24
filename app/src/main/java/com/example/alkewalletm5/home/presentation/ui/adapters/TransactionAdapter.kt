package com.example.alkewalletm5.home.presentation.ui.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.alkeapi.data.network.response.TransactionDataResponse
import com.example.alkewalletm5.R
import com.example.alkewalletm5.databinding.TransactionItemBinding
import com.example.alkewalletm5.home.presentation.viewmodel.HomeViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class TransactionAdapter(
    private val homeViewModel: HomeViewModel,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    var transactions: List<TransactionDataResponse> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
            checkIfEmpty()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val bindingItem = TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(bindingItem)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
    init {
        homeViewModel.transactions.observe(lifecycleOwner, { newTransactions ->
            transactions = newTransactions
        })
    }
    inner class TransactionViewHolder(private val bindingItem: TransactionItemBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(transaction: TransactionDataResponse) {
            bindingItem.apply {
                // Establecer imagen de perfil por defecto
                ImageContact.setImageResource(R.drawable.default_profile_image)
                // Formatear y establecer la fecha de la transacción
                dateTransacction.text = formatDate(transaction.date)

                // Obtener cuenta y usuario relacionados con la transacción
                homeViewModel.getAccountById(transaction.to_account_id)
                homeViewModel.userAccount.observe(lifecycleOwner) { account ->
                    account?.let {
                        // Obtener el usuario asociado a la cuenta
                        homeViewModel.getUserById(account.userId)
                        homeViewModel.userById.observe(lifecycleOwner) { userTransactionList ->
                            userTransactionList?.let { userList ->
                                val userTransaction = userList.find { it.id == account.userId }
                                userTransaction?.let {
                                    // Establecer el nombre completo del contacto
                                    nameContact.text = "${it.first_name} ${it.last_name}"

                                    // Determinar el texto del monto (+ o -)
                                    val amountText = if (homeViewModel.user.value?.id == account.userId) {
                                        "+$${transaction.amount}"
                                    } else {
                                        "-$${transaction.amount}"
                                    }
                                    amountTransacction.text = amountText
                                }
                            }
                        }
                    }
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun formatDate(dateString: String): String {
            val zonedDateTime =
                ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_ZONED_DATE_TIME)
            val desiredFormat = DateTimeFormatter.ofPattern("MMM dd, hh:mm a", Locale.ENGLISH)
            return zonedDateTime.format(desiredFormat)
        }
    }

    private fun checkIfEmpty() {
        if (transactions.isEmpty()) {
            onTransactionsEmptyListener?.invoke(true)
        } else {
            onTransactionsEmptyListener?.invoke(false)
        }
    }

    var onTransactionsEmptyListener: ((Boolean) -> Unit)? = null
}
