package com.example.alkewalletm5.login.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alkewalletm5.data.network.RetrofitHelper
import com.example.alkewalletm5.data.network.AlkeApiService
import com.example.alkewalletm5.data.network.repository.WalletRepositoryImplement
import com.example.alkewalletm5.databinding.ActivityLoginPageBinding
import com.example.alkewalletm5.home.presentation.ui.HomeNavigationActivity
import com.example.alkewalletm5.login.domain.LoginUseCase
import com.example.alkewalletm5.login.presentation.viewmodel.LoginPageViewModel
import com.example.alkewalletm5.login.presentation.viewmodel.LoginPageViewModelFactory
import com.example.alkewalletm5.singup.presentation.ui.SingupActivity

class LoginPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var viewModel: LoginPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViewModelsNavigation()
        binding.loginAccountButton.setOnClickListener {
            login()
        }

        // Código comentado para referencia
        /*binding.loginAccountButton.setOnClickListener {
            val email = binding.enterEmailLogin.text.toString()
            val password = binding.enterPasswordLogin.text.toString()

            if (viewModel.validateCredentials(email, password)) {
                // Inicio de sesión exitoso
                Toast.makeText(this, "Accediendo...", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeNavigationActivity::class.java))
                viewModel.onHomeNavigated()
            } else {
                // Inicio de sesión fallido
                Log.i("Loginnewuser for else", "Email: $email, Password: $password")

                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }*/


    }

    private fun login() {
        val email = binding.enterEmailLogin.text.toString()
        val password = binding.enterPasswordLogin.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModel.login(email, password)
        } else {
            binding.enterEmailLogin.error = "Por favor ingrese un correo electrónico"
            binding.enterPasswordLogin.error = "Por favor ingresa una contraseña"
        }
    }

    private fun initializeViewModelsNavigation() {
        val alkeApiService = RetrofitHelper.getRetrofit(this).create(AlkeApiService::class.java)
        val alkeRepository = WalletRepositoryImplement(alkeApiService)
        val alkeUseCase = LoginUseCase(alkeRepository)
        val loginViewModelFactory = LoginPageViewModelFactory(alkeUseCase, this)

        viewModel =
            ViewModelProvider(this, loginViewModelFactory).get(LoginPageViewModel::class.java)
        viewModel.userProfileSaved.observe(this) { isSaved ->
            if (isSaved) {
                startActivity(Intent(this, HomeNavigationActivity::class.java))
                viewModel.onHomeNavigated()
            } else {
                Toast.makeText(this, "No hay token", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.navigateToSingUpPage.observe(this, Observer { navigate ->
            if (navigate) {
                startActivity(Intent(this, SingupActivity::class.java))
                viewModel.onSingUpPageNavigated()
            }
        })

        binding.createAccountLinkLogin.setOnClickListener {
            viewModel.navigateToSingUpPage()
        }
    }
}
