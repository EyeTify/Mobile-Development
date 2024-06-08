package com.bangkit.eyetify.ui.fragment.article

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.eyetify.R
import com.bangkit.eyetify.data.adapter.ArticleAdapter
import com.bangkit.eyetify.data.adapter.EnsiklopediaAdapter
import com.bangkit.eyetify.data.model.EnsiklopediaModel
import com.bangkit.eyetify.databinding.FragmentArticleBinding
import com.bangkit.eyetify.ui.viewmodel.factory.ArticleViewModelFactory
import com.bangkit.eyetify.ui.viewmodel.model.ArticleViewModel
import com.bangkit.eyetify.data.preference.Result

class ArticleFragment : Fragment() {

    private lateinit var rvEnsiklopedia : RecyclerView
    private val list = ArrayList<EnsiklopediaModel>()
    private lateinit var articleAdapter: ArticleAdapter

    private val articleViewModel by viewModels<ArticleViewModel> {
        ArticleViewModelFactory.getInstance()
    }

    private var _binding: FragmentArticleBinding? = null
    private val binding get()= _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerEnsiklopedia()
        setupRecyclerArticle()
        observeArticles()
        setupSearchView()
        articleViewModel.getAllArticles()
    }

    private fun setupRecyclerEnsiklopedia() {
        rvEnsiklopedia = binding.rvEyetips
        rvEnsiklopedia.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val ensiklopediaAdapter = EnsiklopediaAdapter(list)
        rvEnsiklopedia.adapter = ensiklopediaAdapter

        list.addAll(getListEnsiklopedia())
    }

    private fun setupRecyclerArticle() {
        articleAdapter = ArticleAdapter()
        binding.rvArticle.layoutManager = LinearLayoutManager(requireContext())
        binding.rvArticle.adapter = articleAdapter
    }

    private fun observeArticles() {
        articleViewModel.articles.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.DataLoading -> showLoading(true)
                is Result.DataSuccess -> {
                    showLoading(false)
                    articleAdapter.submitList(result.data)
                }
                is Result.DataError -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    articleViewModel.searchArticles(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    articleViewModel.getAllArticles()
                }
                return true
            }
        })
    }

    @SuppressLint("Recycle")
    private fun getListEnsiklopedia(): ArrayList<EnsiklopediaModel> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataIndication = resources.getStringArray(R.array.data_indication)
        val dataCause = resources.getStringArray(R.array.data_cause)
        val dataService = resources.getStringArray(R.array.data_service)

        return ArrayList<EnsiklopediaModel>().apply {
            for (i in dataName.indices) {
                add(
                    EnsiklopediaModel(
                        dataName[i],
                        dataPhoto.getResourceId(i, -1),
                        dataDescription[i],
                        dataIndication[i],
                        dataCause[i],
                        dataService[i]
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}