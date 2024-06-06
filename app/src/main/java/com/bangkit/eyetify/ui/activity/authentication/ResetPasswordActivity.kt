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
import com.bangkit.eyetify.data.preference.Result
import com.bangkit.eyetify.databinding.ActivityResetPasswordBinding
import com.bangkit.eyetify.ui.viewmodel.factory.AuthViewModelFactory
import com.bangkit.eyetify.ui.viewmodel.model.ResetPasswordViewModel

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding

    private val viewModel by viewModels<ResetPasswordViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
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
        binding.resetPasswordButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            if (email.isNotEmpty()) {
                resetPassword(email)
            } else {
                binding.emailEditTextLayout.error = getString(R.string.invalid_email)
            }
        }
    }

    private fun resetPassword(email: String) {
        viewModel.resetPassword(email)
        viewModel.resetPasswordResult.observe(this) { result ->
            when (result) {
                is Result.DataLoading -> {
                    showLoading(true)
                }
                is Result.DataSuccess -> {
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle("Berhasil")
                        setMessage("Instruksi reset password telah dikirim ke email $email.")
                        setPositiveButton("OK") { _, _ ->
                            val intent = Intent(this@ResetPasswordActivity, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        create()
                        show()
                    }
                }
                is Result.DataError -> {
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle("Reset Gagal")
                        setMessage("Harap Periksa Kembali Email Anda Dengan Benar.")
                        setPositiveButton("OK", null)
                        create()
                        show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}