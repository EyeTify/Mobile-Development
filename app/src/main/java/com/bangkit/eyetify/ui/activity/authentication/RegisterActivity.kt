package com.bangkit.eyetify.ui.activity.authentication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.eyetify.R
import com.bangkit.eyetify.databinding.ActivityRegisterBinding
import com.bangkit.eyetify.ui.viewmodel.factory.AuthViewModelFactory
import com.bangkit.eyetify.ui.viewmodel.model.LoginViewModel
import com.bangkit.eyetify.ui.viewmodel.model.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<RegisterViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    private fun setupAction() {
        val nameVal = binding.nameEditText.text.toString()
        val emailVal = binding.emailEditText.text.toString()
        val passVal = binding.passwordEditText.text.toString()
        val confirmPassVal = binding.passwordConfirmationEditText.text.toString()

        binding.signupButton.setOnClickListener{
            if (nameVal.isNotEmpty() && emailVal.isNotEmpty() &&
                passVal.isNotEmpty() && confirmPassVal.isNotEmpty()){

                if (passVal == confirmPassVal){
                    processRegister()
                    showLoading(true)
                }else{
                    binding.passwordEditText.error = "Pastikan Passowrd Yang Dimasukkan Sama Ya....."
                }

            }
        }

    }

    private fun processRegister() {
        apply {
            viewModel.register(
                binding.nameEditText.text.toString(),
                binding.emailEditText.toString(),
                binding.passwordEditText.text.toString()
            )
            showLoading(false)

            AlertDialog.Builder(this).apply {
                setTitle("Yeah!")
                setMessage("Akun ${binding.nameEditText.text.toString()} sudah jadi nih. Yuk Login Dulu.")
                setPositiveButton("Lanjut") { _, _ ->
                    finish()
                    intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
                create()
                show()
            }


        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}