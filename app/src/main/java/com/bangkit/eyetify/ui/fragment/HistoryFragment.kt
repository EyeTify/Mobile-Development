package com.bangkit.eyetify.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.eyetify.data.adapter.ResultSavedAdapter
import com.bangkit.eyetify.data.repository.ResultRepository
import com.bangkit.eyetify.data.room.AppDatabase
import com.bangkit.eyetify.databinding.FragmentHistoryBinding
import com.bangkit.eyetify.ui.viewmodel.factory.ResultViewModelFactory
import com.bangkit.eyetify.ui.viewmodel.model.HistoryViewModel
import com.justin.popupbarchart.GraphValue

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: ResultSavedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = AppDatabase.getInstance(requireContext()).resultDao()
        val repository = ResultRepository.getInstance(dao)
        val viewModelFactory = ResultViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HistoryViewModel::class.java]

        val customBarchart = binding.customBarchart
        customBarchart.setGraphValues(
            arrayListOf(
                GraphValue(day = 1, id = 1, progress = 30, isToday = false, showToolTip = false),
                GraphValue(day = 2, id = 2, progress = 70, isToday = false, showToolTip = false),
                GraphValue(day = 3, id = 3, progress = 100, isToday = false, showToolTip = false),
                GraphValue(day = 4, id = 4, progress = 0, isToday = false, showToolTip = false),
                GraphValue(day = 5, id = 5, progress = 50, isToday = false, showToolTip = false),
                GraphValue(day = 6, id = 6, progress = 50, isToday = false, showToolTip = false),
                GraphValue(day = 7, id = 7, progress = 25, isToday = false, showToolTip = false)
            )
        )

        viewModel.getAllSavedResult().observe(viewLifecycleOwner) { resultList ->
            if (resultList.isEmpty()) {
                binding.tvEmptyList.visibility = View.VISIBLE
                binding.rvHistory.visibility = View.GONE
            } else {
                binding.tvEmptyList.visibility = View.GONE
                binding.rvHistory.visibility = View.VISIBLE
                adapter = ResultSavedAdapter(resultList.toMutableList(), viewModel)
                binding.rvHistory.adapter = adapter
            }
        }

        binding.rvHistory.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
