package com.bangkit.eyetify.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bangkit.eyetify.MainActivity
import com.bangkit.eyetify.R
import com.bangkit.eyetify.data.room.ResultEntity
import com.bangkit.eyetify.databinding.ActivityResultBinding
import com.bangkit.eyetify.ui.viewmodel.factory.ResultViewModelFactory
import com.bangkit.eyetify.ui.viewmodel.model.HistoryViewModel
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    private val viewModel by viewModels<HistoryViewModel> {
        ResultViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Mendapatkan hasil analisis dari intent
        val result = intent.getStringExtra("result")
        val suggestion = intent.getStringExtra("suggestion")
        val imageresult = intent.getStringExtra("imageExtra")

        // Menampilkan hasil analisis
        binding.leftCardValue.text = result
        binding.rightCardValue.text = suggestion
        binding.imageResult.setImageURI(imageresult?.toUri())

        binding.fabSave.setOnClickListener {
            saveResult(result, suggestion, imageresult)
        }

    }

    private fun saveResult(result: String?, suggestion: String?, imageUri: String?) {
        if (result != null && suggestion != null && imageUri != null) {
            val resultEntity = ResultEntity(
                result = result,
                suggestion = suggestion,
                imageUri = imageUri
            )
            viewModel.insertSavedResult(resultEntity)
            showToast("Result saved successfully.")
            navigateToHistoryFragment()
        } else {
            showToast("Error: Missing result data.")
        }
    }

    private fun navigateToHistoryFragment() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("navigateToHistory", true)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}