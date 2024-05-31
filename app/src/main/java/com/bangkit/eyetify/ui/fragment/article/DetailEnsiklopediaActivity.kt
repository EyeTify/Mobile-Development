package com.bangkit.eyetify.ui.fragment.article

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.eyetify.R
import com.bangkit.eyetify.data.model.EnsiklopediaModel
import com.bangkit.eyetify.databinding.ActivityDetailEnsiklopediaBinding

class DetailEnsiklopediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEnsiklopediaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEnsiklopediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataEnsiklopedia = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<EnsiklopediaModel>(key_ensiklopedia, EnsiklopediaModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<EnsiklopediaModel>(key_ensiklopedia)
        }

        dataEnsiklopedia?.let {
            binding.tvTitledesease.text = it.name
            binding.imgEyedesease.setImageResource(it.photo)
            binding.tvDesciptiondesease.text = it.description
        }
    }

    companion object {
        const val key_ensiklopedia = "key_ensiklopedia"
    }
}