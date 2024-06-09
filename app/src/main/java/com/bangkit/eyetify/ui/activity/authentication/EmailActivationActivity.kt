package com.bangkit.eyetify.ui.activity.authentication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.eyetify.R
import com.bangkit.eyetify.databinding.ActivityEmailActivationBinding
import com.bangkit.eyetify.ui.activity.WelcomeActivity

class EmailActivationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailActivationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailActivationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
    }
}