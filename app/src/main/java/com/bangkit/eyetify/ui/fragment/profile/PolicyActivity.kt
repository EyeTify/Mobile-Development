package com.bangkit.eyetify.ui.fragment.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.eyetify.R
import com.bangkit.eyetify.databinding.ActivityPolicyBinding

class PolicyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener {
            onBackPressed()
        }

        // aksi klik pada image_email
        binding.imageEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:ammarhamdim@gmail.com")
            startActivity(intent)
        }

        // aksi klik pada image_contact
        binding.imageContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("tel:+6281277020502")
            startActivity(intent)
        }
    }

    // intent email
    fun sendEmail(view: View) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:ammarhamdim@gmail.com")
        startActivity(intent)
    }

    // intent kontak
    fun openContacts(view: View) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("tel:+6281277020502")
        startActivity(intent)
    }
}
