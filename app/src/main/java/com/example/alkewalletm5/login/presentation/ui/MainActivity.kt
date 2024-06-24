package com.example.alkewalletm5.login.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alkewalletm5.R
import com.example.alkewalletm5.databinding.ActivityMainBinding
import com.example.alkewalletm5.login.presentation.viewmodel.MainActivityViewModel

/**
 * Actividad que se muestra al iniciar la aplicación es simplemente una imagen que escucha en su contenido
 * un click para navegar a la siguiente actividad
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.navigateToIntroAccount.observe(this, Observer { navigate ->
            if (navigate) {
                startActivity(Intent(this, IntroAccountActivity::class.java))
                viewModel.onIntroAccountNavigated()
            }
        })

        binding.firstScreen.setOnClickListener {
            viewModel.navigateToIntroAccount()
        }
    }
}