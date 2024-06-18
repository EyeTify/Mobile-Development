package com.bangkit.eyetify

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.bangkit.eyetify.databinding.ActivityMainBinding
import com.bangkit.eyetify.ui.activity.WelcomeActivity
import com.bangkit.eyetify.ui.fragment.HistoryFragment
import com.bangkit.eyetify.ui.fragment.HomeFragment
import com.bangkit.eyetify.ui.fragment.profile.ProfileFragment
import com.bangkit.eyetify.ui.fragment.ScanFragment
import com.bangkit.eyetify.ui.fragment.article.ArticleFragment
import com.bangkit.eyetify.ui.viewmodel.factory.AuthViewModelFactory
import com.bangkit.eyetify.ui.viewmodel.model.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        CoroutineScope(Dispatchers.Main).launch {
            delay(1500) // Delay for 1500 milliseconds
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
        setupView()
        switchFragment(HomeFragment())

        val fromFragmentArticle = intent.getBooleanExtra("navigateToArticle", false)
        val fromResult = intent.getBooleanExtra("navigateToHistory", false)
        val fromScan = intent.getBooleanExtra("navigateToScan", false)
        if (fromFragmentArticle) {
            navigateToArticleFragment()
        }

        if (fromResult) {
            navigateToHistoryFragment()
        }

        if (fromScan){
            navigateToScanFragment()
        }

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

        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, HomeFragment())
            .commit()
    }

    private fun navigateToArticleFragment() {
        switchFragment(ArticleFragment())
        setSelectedItem(R.id.action_article)
    }

    private fun navigateToHistoryFragment() {
        switchFragment(HistoryFragment())
        setSelectedItem(R.id.action_history)
    }

    private fun navigateToScanFragment() {
        switchFragment(ScanFragment())
        setSelectedItem(R.id.action_scan)
    }

    private fun setSelectedItem(itemId: Int) {
        binding.navigateMenu.selectedItemId = itemId
    }
}