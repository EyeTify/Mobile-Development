package com.bangkit.eyetify.ui.fragment.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.eyetify.R
import com.bangkit.eyetify.data.injection.adapter.EnsiklopediaAdapter
import com.bangkit.eyetify.data.model.EnsiklopediaModel
import com.bangkit.eyetify.databinding.FragmentArticleBinding
class ArticleFragment : Fragment() {

    private lateinit var rvEnsiklopedia : RecyclerView
    private val list = ArrayList<EnsiklopediaModel>()

    private var _binding: FragmentArticleBinding? = null
    private val binding get()= _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvEnsiklopedia = binding.rvEyetips

        list.addAll(getListEnsiklopedia())
        showRecyclerList()
    }

    private fun getListEnsiklopedia(): ArrayList<EnsiklopediaModel> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val listEnsiklopedia = ArrayList<EnsiklopediaModel>()
        for (i in dataName.indices) {
            val ensiklopedia = EnsiklopediaModel(dataName[i], dataPhoto.getResourceId(i, -1), dataDescription[i])
            listEnsiklopedia.add(ensiklopedia)
        }
        return listEnsiklopedia
    }

    private fun showRecyclerList() {
        rvEnsiklopedia.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val listMartialAdapter = EnsiklopediaAdapter(list)
        rvEnsiklopedia.adapter = listMartialAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}