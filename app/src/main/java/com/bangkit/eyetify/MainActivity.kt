package com.bangkit.eyetify

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bangkit.eyetify.databinding.ActivityMainBinding
import com.bangkit.eyetify.ui.activity.WelcomeActivity
import com.bangkit.eyetify.ui.fragment.ArticleFragment
import com.bangkit.eyetify.ui.fragment.HistoryFragment
import com.bangkit.eyetify.ui.fragment.HomeFragment
import com.bangkit.eyetify.ui.fragment.ProfileFragment
import com.bangkit.eyetify.ui.fragment.ScanFragment
import com.bangkit.eyetify.ui.viewmodel.factory.AuthViewModelFactory
import com.bangkit.eyetify.ui.viewmodel.model.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        switchFragment(HomeFragment())

        binding.navigateMenu.setOnItemSelectedListener {
            when(it.itemId){
                R.id.action_home -> switchFragment(HomeFragment())

                R.id.action_history -> switchFragment(HistoryFragment())

                R.id.action_scan -> switchFragment(ScanFragment())

                R.id.action_article -> switchFragment(ArticleFragment())

                R.id.action_profile -> switchFragment(ProfileFragment())
            }
            true
        }

    }

    private fun switchFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container_fragment, fragment)
        fragmentTransaction.commit()
    }
}