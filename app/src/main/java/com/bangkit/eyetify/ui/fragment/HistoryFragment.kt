package com.bangkit.eyetify.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.eyetify.R
import com.bangkit.eyetify.databinding.FragmentHistoryBinding
import com.justin.popupbarchart.GraphValue

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}