package com.bangkit.eyetify.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.bangkit.eyetify.R
import com.bangkit.eyetify.databinding.FragmentScanBinding
import com.bangkit.eyetify.ui.activity.ResultActivity
import com.bangkit.eyetify.ui.viewmodel.factory.AuthViewModelFactory
import com.bangkit.eyetify.ui.viewmodel.model.MainViewModel
import com.bangkit.eyetify.ui.viewmodel.model.RegisterViewModel

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get()= _binding!!

    private val viewModel by viewModels<MainViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addImageButton.setOnClickListener{
            val intent = Intent(this.context, ResultActivity::class.java)
            startActivity(intent)
        }

        binding.analyzeImageButton.setOnClickListener{
            viewModel.logout()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScanFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}