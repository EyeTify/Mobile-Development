package com.bangkit.eyetify.ui.fragment.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.eyetify.databinding.ActivityPrivacyBinding

class PrivacyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrivacyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyBinding.inflate(layoutInflater)
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
