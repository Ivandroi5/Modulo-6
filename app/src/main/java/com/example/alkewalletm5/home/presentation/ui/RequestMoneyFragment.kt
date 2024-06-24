package com.example.alkewalletm5.home.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.alkewalletm5.R
import com.example.alkewalletm5.databinding.FragmentRequestMoneyBinding

/**
 * Fragmento para solicitar dinero lleva la navegación para volver al Home
 */
class RequestMoneyFragment : Fragment() {
    private lateinit var binding: FragmentRequestMoneyBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestMoneyBinding.inflate(inflater, container, false)
        val btnNavRecieve: Button = binding.buttonRecieveMoney
        val btnNavBackHome: ImageButton = binding.backArrowRequestMoney

        btnNavRecieve.setOnClickListener {
            //hacer lógica de depósito
            findNavController().navigate(R.id.action_requestMoneyFragment2_to_homeFragment)
        }
        btnNavBackHome.setOnClickListener {
            findNavController().navigate(R.id.action_requestMoneyFragment2_to_homeFragment)
        }


        return binding.root
    }

}
