package com.bangkit.eyetify.ui.fragment

import android.app.Dialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bangkit.eyetify.R
import com.bangkit.eyetify.databinding.FragmentProfileBinding
import com.bangkit.eyetify.ui.viewmodel.factory.AuthViewModelFactory
import com.bangkit.eyetify.ui.viewmodel.model.LoginViewModel
import com.bangkit.eyetify.ui.viewmodel.model.MainViewModel
import com.bangkit.eyetify.ui.viewmodel.model.RegisterViewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }

    private val nameviewModel by viewModels<LoginViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayProfile()

        binding.profileCardRating.setOnClickListener{
            showPopup()
        }

        binding.profileCardMyprofile.setOnClickListener{
            showPopup()
        }

        binding.profileCardPrivacy.setOnClickListener{
            showPopup()
        }

        binding.profileCardRequirement.setOnClickListener{
            showPopup()
        }

        binding.cardLogout.setOnClickListener{
            viewModel.logout()
        }
    }

    private fun showPopup() {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.pop_up_announcement)
        dialog.show()

        val btnClose = dialog.findViewById<View>(R.id.btn_close_announcement)

        btnClose.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun loadUsername(): String? {
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }

    private fun loadEmail(): String? {
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", null)
    }

    private fun displayProfile() {
        val username = loadUsername()
        val email = loadEmail()

        if (username != null) {
            binding.nameProfile.text = username
        } else {
            binding.nameProfile.text = getString(R.string.username_not_found)
        }

        if (email != null) {
            binding.emailProfile.text = email
        } else {
            binding.emailProfile.text = getString(R.string.email_not_found)
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}