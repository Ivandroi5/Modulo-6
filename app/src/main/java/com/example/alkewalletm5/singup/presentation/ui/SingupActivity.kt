package com.example.alkewalletm5.singup.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alkeapi.data.network.response.UserPost
import com.example.alkewalletm5.data.network.AlkeApiService
import com.example.alkewalletm5.data.network.RetrofitHelper
import com.example.alkewalletm5.data.network.repository.WalletRepositoryImplement
import com.example.alkewalletm5.databinding.ActivitySingupBinding
import com.example.alkewalletm5.login.presentation.ui.LoginPageActivity
import com.example.alkewalletm5.singup.domain.SingupUserCase
import com.example.alkewalletm5.singup.presentation.viewmodel.SignupViewModelFactory
import com.example.alkewalletm5.singup.presentation.viewmodel.SingupViewModel

class SingupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingupBinding
    private lateinit var viewModel: SingupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val alkeApiService = RetrofitHelper.getRetrofit(this).create(AlkeApiService::class.java)
        val walletRepository = WalletRepositoryImplement(alkeApiService)
        val singupUserCase = SingupUserCase(walletRepository)
        val singupViewModelFactory =SignupViewModelFactory(singupUserCase, this)

        viewModel = ViewModelProvider(this, singupViewModelFactory).get(SingupViewModel::class.java)

        viewModel.navigateToLoginPage.observe(this, Observer { navigate ->
            if (navigate) {
                startActivity(Intent(this, LoginPageActivity::class.java))
                viewModel.onLoginPageNavigated()
            }
        })

        binding.createAccountFinal.setOnClickListener {
            if (validateFields() ) {
                viewModel.navigateToLoginPage()
            }
        }
        binding.loginAccountSingup.setOnClickListener {
            viewModel.navigateToLoginPage()
        }
    }

    /**
     * Valida los campos del formulario de registro y agrega la informacion al viewModel
     */
    private fun validateFields(): Boolean {
        var error: String? = null
        val name = binding.textInputName.editText?.text.toString()
        val lastName = binding.textInputLayoutLastName.editText?.text.toString()
        val email = binding.editTextTextEmailAddress.editText?.text.toString()
        val password = binding.editTextTextPassword3.editText?.text.toString()
        val confirmPassword = binding.textInputLayout3.editText?.text.toString()

        return if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            error = "Se requiere llenar todos los campos para continuar"
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            false
        } else if (!validatePasswords()) {
            // validatePasswords() retorna false si las contraseñas no coinciden
            false
        } else {
            val newUser = UserPost(name, lastName, email, password)
            viewModel.createUser(newUser)
            //viewModel.createAccount()
            Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()
            true

        }
    }

    /**
     * Valida que las contraseñas coincidan
     */
    private fun validatePasswords(): Boolean {
        var error: String? = null
        val password = binding.editTextTextPassword3.editText?.text.toString()
        val confirmPassword = binding.textInputLayout3.editText?.text.toString()

        return if (password == confirmPassword) {
            true
        } else {
            error = "Las contraseñas no coinciden"
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            false
        }
    }

}