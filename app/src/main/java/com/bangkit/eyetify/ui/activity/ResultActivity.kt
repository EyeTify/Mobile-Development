package com.bangkit.eyetify.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.eyetify.MainActivity
import com.bangkit.eyetify.R
import com.bangkit.eyetify.data.adapter.ArticleAdapter
import com.bangkit.eyetify.data.preference.Result
import com.bangkit.eyetify.data.room.ResultEntity
import com.bangkit.eyetify.databinding.ActivityResultBinding
import com.bangkit.eyetify.ui.viewmodel.factory.ArticleViewModelFactory
import com.bangkit.eyetify.ui.viewmodel.factory.ResultViewModelFactory
import com.bangkit.eyetify.ui.viewmodel.model.ArticleViewModel
import com.bangkit.eyetify.ui.viewmodel.model.HistoryViewModel
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var articleAdapter: ArticleAdapter

    private val viewModel by viewModels<HistoryViewModel> {
        ResultViewModelFactory.getInstance(this)
    }

    private val articleViewModel by viewModels<ArticleViewModel> {
        ArticleViewModelFactory.getInstance()
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
            binding.fabSave.setImageResource(R.drawable.ic_download_succes)
        }

        setupRecyclerArticle()
        observeArticles()
        articleViewModel.getAllArticles()
    }

    private fun setupRecyclerArticle() {
        articleAdapter = ArticleAdapter()
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = articleAdapter
    }

    private fun observeArticles() {
        articleViewModel.articles.observe(this) { result ->
            when (result) {
                is Result.DataLoading -> showLoading(true)
                is Result.DataSuccess -> {
                    showLoading(false)
                    articleAdapter.submitList(result.data)
                }
                is Result.DataError -> {
                    showLoading(false)
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
           binding.loadingState.visibility = View.VISIBLE
        } else {
            binding.loadingState.visibility = View.GONE
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