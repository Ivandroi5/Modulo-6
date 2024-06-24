package com.example.alkewalletm5.home.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alkewalletm5.R
import com.example.alkewalletm5.application.SharedPreferencesHelper
import com.example.alkewalletm5.data.local.database.AppDatabase
import com.example.alkewalletm5.databinding.FragmentHomeBinding
import com.example.alkewalletm5.home.presentation.ui.adapters.TransactionAdapter
import com.example.alkewalletm5.data.local.entities.Profile
import com.example.alkewalletm5.data.local.repository.TransactionR
import com.example.alkewalletm5.data.network.AlkeApiService
import com.example.alkewalletm5.data.network.RetrofitHelper
import com.example.alkewalletm5.data.network.repository.WalletRepositoryImplement
import com.example.alkewalletm5.home.domain.DbUseCase
import com.example.alkewalletm5.home.domain.HomeUseCase
import com.example.alkewalletm5.home.presentation.ui.adapters.ProfileAdapter
import com.example.alkewalletm5.home.presentation.viewmodel.HomeViewModel
import com.example.alkewalletm5.home.presentation.viewmodel.HomeViewModelFactory

/**
 * Fragmento para la página principal del Home del usuario
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initializeViewModels()
        setupViews()
        initAdapters()

        return binding.root
    }

    private fun initializeViewModels() {
        val context = requireContext()
        val alkeApiService = RetrofitHelper.getRetrofit(context).create(AlkeApiService::class.java)
        val walletRepository = WalletRepositoryImplement(alkeApiService)
        val alkeUseCase = HomeUseCase(walletRepository)

        val database = AppDatabase.getDatabase(requireContext())
        val databaseRepository = TransactionR(database.transactionDao())
        val databaseUseCase = DbUseCase(databaseRepository)

        val viewModelFactory = HomeViewModelFactory(alkeUseCase, databaseUseCase)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private fun setupViews() {
        binding.recyclerViewTransaction.layoutManager = LinearLayoutManager(requireContext())
        binding.frameLayoutInfoProfile.layoutManager = LinearLayoutManager(requireContext())

        val btnNavSend: Button = binding.travelToSendMoney
        val btnNavReceive: Button = binding.travelToReceiveMoney

        btnNavSend.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_sendMoneyFragment2)
        }
        btnNavReceive.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_requestMoneyFragment2)
        }
    }

    private fun initAdapters() {
        transactionAdapter = TransactionAdapter(homeViewModel, viewLifecycleOwner)
        binding.recyclerViewTransaction.adapter = transactionAdapter

        // transactionAdapter.transaction = Transaction.datatransaction
        // transactionAdapter.transaction = Transaction.dataTransactionEmpty

        if (transactionAdapter.transactions.isEmpty()) {
            binding.imageNoTransaction.visibility = View.VISIBLE
            binding.textNoTransaction.visibility = View.VISIBLE
        } else {
            binding.imageNoTransaction.visibility = View.GONE
            binding.textNoTransaction.visibility = View.GONE
        }

        val profileAdapter = ProfileAdapter ()
        binding.frameLayoutInfoProfile.adapter = profileAdapter
        profileAdapter.profiles = Profile.dataProfiles
        profileAdapter.onItemClickListener = { profile ->
            Toast.makeText(
                requireContext(), "Navegando hacia el perfil",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_homeFragment_to_profilePageFragment2)

        }
        if (profileAdapter.profiles.isEmpty()) {
            binding.noProfile.visibility = View.VISIBLE

        } else {
            binding.noProfile.visibility = View.GONE

        }

    }
    override fun onDestroy() {
        super.onDestroy()
        SharedPreferencesHelper.clearUserData(requireContext())
        SharedPreferencesHelper.clearToken(requireContext())
    }
}


