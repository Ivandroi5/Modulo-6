package com.example.alkewalletm5.home.presentation.ui

import ContactAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.alkeapi.data.network.response.AccountDataResponse
import com.example.alkeapi.data.network.response.TransactionPost
import com.example.alkewalletm5.R
import com.example.alkewalletm5.data.local.database.AppDatabase
import com.example.alkewalletm5.data.local.model.Transaction
import com.example.alkewalletm5.data.local.model.User
import com.example.alkewalletm5.data.local.repository.TransactionR
import com.example.alkewalletm5.data.network.AlkeApiService
import com.example.alkewalletm5.data.network.RetrofitHelper
import com.example.alkewalletm5.data.network.repository.WalletRepositoryImplement
import com.example.alkewalletm5.databinding.FragmentSendMoneyBinding
import com.example.alkewalletm5.home.domain.DbUseCase
import com.example.alkewalletm5.home.domain.SendMoneyUseCase
import com.example.alkewalletm5.home.presentation.viewmodel.SendMoneyViewModel
import com.example.alkewalletm5.home.presentation.viewmodel.SendMoneyViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SendMoneyFragment : Fragment() {

    private lateinit var binding: FragmentSendMoneyBinding
    private lateinit var sendMoneyViewModel: SendMoneyViewModel
    private lateinit var validUsers: List<User>
    private lateinit var contactAdapter: ContactAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSendMoneyBinding.inflate(inflater, container, false)
        setupViews()
        setupViewModel()
        observeViewModel()
        setupSpinnerListener()
        return binding.root
    }

    private fun setupViews() {
        binding.apply {
            buttonSendMoney.setOnClickListener { onSendMoneyClick() }
            backArrowSendMoney.setOnClickListener { navigateToHomeFragment() }
        }
    }

    private fun setupViewModel() {
        val context = requireContext()
        val alkeApiService = RetrofitHelper.getRetrofit(context).create(AlkeApiService::class.java)
        val walletRepository = WalletRepositoryImplement(alkeApiService)
        val sendMoneyUseCase = SendMoneyUseCase(walletRepository)

        val database = AppDatabase.getDatabase(requireContext())
        val databaseRepository = TransactionR(database.transactionDao())
        val databaseUseCase = DbUseCase(databaseRepository)

        val viewModelFactory = SendMoneyViewModelFactory(sendMoneyUseCase, databaseUseCase)

        sendMoneyViewModel = ViewModelProvider(this, viewModelFactory)[SendMoneyViewModel::class.java]
    }

    private fun observeViewModel() {
        sendMoneyViewModel.apply {
            accountResult.observe(viewLifecycleOwner, Observer { accounts ->
                accounts?.let {
                    usersResult.observe(viewLifecycleOwner, Observer { users ->
                        users?.let {
                            validUsers = users.filter { it.first_name != null }
                            contactAdapter = ContactAdapter(requireContext(), accounts, validUsers)
                            binding.spinnerSendMoney.adapter = contactAdapter
                        }
                    })
                }
            })

            transactionResult.observe(viewLifecycleOwner, Observer { transactionSuccess ->
                val message = if (transactionSuccess) "Transacción exitosa" else "Transacción fallida"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                navigateToHomeFragment()
            })
        }
    }

    private fun setupSpinnerListener() {
        binding.spinnerSendMoney.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                parent?.let {
                    val selectedAccount = it.getItemAtPosition(position) as? AccountDataResponse
                    selectedAccount?.let { account ->
                        val selectedUser = validUsers.find { it.id == account.userId }
                        selectedUser?.let {
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hay usuario seleccionado
            }
        }
    }


    private fun onSendMoneyClick() {
        val selectedAccount = binding.spinnerSendMoney.selectedItem as AccountDataResponse
        val amountText = binding.amoutSendMoney.editText?.text.toString()
        val concept = binding.imputNotas.editText?.text.toString()
        val userSelected = validUsers.find { it.id == selectedAccount.userId }

        if (amountText.isEmpty() || concept.isEmpty()) {
            Toast.makeText(requireContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toInt()
        val date = Date()
        val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date)

        val account = sendMoneyViewModel.account.value
        val user = sendMoneyViewModel.user.value

        if (account != null && user != null && userSelected != null) {
            val newTransaction = TransactionPost(
                amount,
                concept,
                formattedDate,
                type = "transfer",
                account.id,
                user.id,
                selectedAccount.id
            )

            val newTransactionDb = Transaction(
                sender_name = user.first_name + " " + user.last_name,
                receiver_name = userSelected.first_name + " " + userSelected.last_name,
                transacion_date = formattedDate,
                isReceiver = false,
                amount = amount.toDouble(),
                concept = concept
            )

            sendMoneyViewModel.insertTransaction(newTransactionDb)
            sendMoneyViewModel.createTransaction(newTransaction)
        } else {
            Toast.makeText(requireContext(), "No se encontraron datos de cuenta", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.homeFragment)
    }
}
