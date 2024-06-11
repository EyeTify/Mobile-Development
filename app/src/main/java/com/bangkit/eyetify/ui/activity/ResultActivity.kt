package com.bangkit.eyetify.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.eyetify.R
import com.bangkit.eyetify.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
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
    }
}