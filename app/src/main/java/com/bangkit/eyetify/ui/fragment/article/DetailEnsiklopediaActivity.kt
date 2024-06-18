package com.bangkit.eyetify.ui.fragment.article

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.eyetify.MainActivity
import com.bangkit.eyetify.R
import com.bangkit.eyetify.data.model.EnsiklopediaModel
import com.bangkit.eyetify.databinding.ActivityDetailEnsiklopediaBinding

class DetailEnsiklopediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEnsiklopediaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEnsiklopediaBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val dataEnsiklopedia = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<EnsiklopediaModel>(key_ensiklopedia, EnsiklopediaModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<EnsiklopediaModel>(key_ensiklopedia)
        }

        dataEnsiklopedia?.let {
            binding.tvTitleEyeDisease.text = it.name
            binding.imgEyeDisease.setImageResource(it.photo)
            binding.tvDescriptionEyeDisease.text = it.description
            binding.tvIndicationEyeDisease.text = it.indication
            binding.tvCauseEyeDisease.text = it.cause
            binding.tvServiceEyeDisease.text = it.service
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("OPEN_ARTIKEL_FRAGMENT", true)
            startActivity(intent)
        }
    }

    companion object {
        const val key_ensiklopedia = "key_ensiklopedia"
    }
}