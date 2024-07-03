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

/**
 * Actividad de la página de inicio de sesión.
 * Permite al usuario ingresar sus credenciales para iniciar sesión.
 */
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
    }

    /**
     * Método para manejar la acción de inicio de sesión.
     * Valida los campos de correo electrónico y contraseña, y llama al ViewModel para iniciar sesión.
     */
    private fun login() {
        val email = binding.enterEmailLogin.text.toString()
        val password = binding.enterPasswordLogin.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModel.login(email, password)
        } else {
            // Mostrar error si el campo de correo electrónico o contraseña está vacío
            binding.enterEmailLogin.error = "Por favor ingrese un correo electrónico"
            binding.enterPasswordLogin.error = "Por favor ingresa una contraseña"
        }
    }

    /**
     * Inicializa ViewModel y establece observadores para la navegación y eventos del ViewModel.
     */
    private fun initializeViewModelsNavigation() {
        // Configurar ViewModel y ViewModelFactory para la página de inicio de sesión
        val alkeApiService = RetrofitHelper.getRetrofit(this).create(AlkeApiService::class.java)
        val alkeRepository = WalletRepositoryImplement(alkeApiService)
        val alkeUseCase = LoginUseCase(alkeRepository)
        val loginViewModelFactory = LoginPageViewModelFactory(alkeUseCase, this)

        // Inicializar ViewModel utilizando ViewModelProvider y ViewModelFactory
        viewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginPageViewModel::class.java)

        // Observar el LiveData para la navegación al HomeNavigationActivity después del inicio de sesión exitoso
        viewModel.userProfileSaved.observe(this) { isSaved ->
            if (isSaved) {
                startActivity(Intent(this, HomeNavigationActivity::class.java))
                viewModel.onHomeNavigated()
            } else {
                Toast.makeText(this, "No hay token", Toast.LENGTH_SHORT).show()
            }
        }

        // Observar el LiveData para la navegación a SingupActivity al hacer clic en "Crear cuenta"
        viewModel.navigateToSingUpPage.observe(this, Observer { navigate ->
            if (navigate) {
                startActivity(Intent(this, SingupActivity::class.java))
                viewModel.onSingUpPageNavigated()
            }
        })

        // Configurar clic listener para el enlace "Crear cuenta" que llama a navigateToSingUpPage en el ViewModel
        binding.createAccountLinkLogin.setOnClickListener {
            viewModel.navigateToSingUpPage()
        }
    }
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