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

/**
 * Fragmento para la funcionalidad de enviar dinero.
 * Permite al usuario seleccionar una cuenta y un usuario válido para realizar la transacción.
 */
class SendMoneyFragment : Fragment() {

    private lateinit var binding: FragmentSendMoneyBinding
    private lateinit var sendMoneyViewModel: SendMoneyViewModel
    private lateinit var validUsers: List<User>
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar y vincular el layout del fragmento
        binding = FragmentSendMoneyBinding.inflate(inflater, container, false)

        // Configurar vistas, ViewModel y observadores
        setupViews()
        setupViewModel()
        observeViewModel()
        setupSpinnerListener()

        return binding.root
    }

    /**
     * Configura las vistas y sus interacciones.
     */
    private fun setupViews() {
        binding.apply {
            // Configurar clic listeners para botones
            buttonSendMoney.setOnClickListener { onSendMoneyClick() }
            backArrowSendMoney.setOnClickListener { navigateToHomeFragment() }
        }
    }

    /**
     * Inicializa el ViewModel necesario para este fragmento.
     */
    private fun setupViewModel() {
        // Configuración del ViewModel de SendMoneyViewModel
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

    /**
     * Observa los LiveData en el ViewModel para actualizar la interfaz de usuario según los cambios.
     */
    private fun observeViewModel() {
        sendMoneyViewModel.apply {
            // Observar la lista de cuentas y usuarios válidos para configurar el adapter del spinner
            accountResult.observe(viewLifecycleOwner, Observer { accounts ->
                accounts?.let {
                    usersResult.observe(viewLifecycleOwner, Observer { users ->
                        users?.let {
                            // Filtrar usuarios válidos y configurar el adapter del spinner
                            validUsers = users.filter { it.first_name != null }
                            contactAdapter = ContactAdapter(requireContext(), accounts, validUsers)
                            binding.spinnerSendMoney.adapter = contactAdapter
                        }
                    })
                }
            })

            // Observar el resultado de la transacción para mostrar un mensaje y navegar de vuelta a la página principal
            transactionResult.observe(viewLifecycleOwner, Observer { transactionSuccess ->
                val message = if (transactionSuccess) "Transacción exitosa" else "Transacción fallida"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                navigateToHomeFragment()
            })
        }
    }

    /**
     * Configura el listener para el spinner de selección de cuenta.
     */
    private fun setupSpinnerListener() {
        binding.spinnerSendMoney.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                parent?.let {
                    // Obtener la cuenta seleccionada del spinner
                    val selectedAccount = it.getItemAtPosition(position) as? AccountDataResponse
                    selectedAccount?.let { account ->
                        // Buscar el usuario asociado a la cuenta seleccionada
                        val selectedUser = validUsers.find { it.id == account.userId }
                        selectedUser?.let {
                            // Realizar acciones adicionales si es necesario
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hay cuenta seleccionada
            }
        }
    }

    /**
     * Acción realizada al hacer clic en el botón de enviar dinero.
     * Realiza la validación de campos y crea una nueva transacción si los datos son válidos.
     */
    private fun onSendMoneyClick() {
        val selectedAccount = binding.spinnerSendMoney.selectedItem as AccountDataResponse
        val amountText = binding.amoutSendMoney.editText?.text.toString()
        val concept = binding.imputNotas.editText?.text.toString()
        val userSelected = validUsers.find { it.id == selectedAccount.userId }

        // Validar que los campos de monto y concepto no estén vacíos
        if (amountText.isEmpty() || concept.isEmpty()) {
            Toast.makeText(requireContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Convertir el monto a un valor entero
        val amount = amountText.toInt()

        // Obtener la fecha actual formateada
        val date = Date()
        val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date)

        // Obtener datos de cuenta y usuario del ViewModel
        val account = sendMoneyViewModel.account.value
        val user = sendMoneyViewModel.user.value

        // Verificar que los datos de cuenta y usuario no sean nulos y que el usuario seleccionado sea válido
        if (account != null && user != null && userSelected != null) {
            // Crear objeto TransactionPost para enviar al servidor
            val newTransaction = TransactionPost(
                amount,
                concept,
                formattedDate,
                type = "transfer",
                account.id,
                user.id,
                selectedAccount.id
            )

            // Crear objeto Transaction para insertar en la base de datos local
            val newTransactionDb = Transaction(
                sender_name = user.first_name + " " + user.last_name,
                receiver_name = userSelected.first_name + " " + userSelected.last_name,
                transacion_date = formattedDate,
                isReceiver = false,
                amount = amount.toDouble(),
                concept = concept
            )

            // Insertar la transacción en la base de datos local y enviar la transacción al servidor
            sendMoneyViewModel.insertTransaction(newTransactionDb)
            sendMoneyViewModel.createTransaction(newTransaction)
        } else {
            Toast.makeText(requireContext(), "No se encontraron datos de cuenta", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Navega de vuelta a la página principal.
     */
    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.homeFragment)
    }
}

