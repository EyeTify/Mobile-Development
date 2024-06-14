package com.bangkit.eyetify.ui.fragment.profile

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.bangkit.eyetify.R
import com.bangkit.eyetify.databinding.FragmentProfileBinding
import com.bangkit.eyetify.ui.viewmodel.factory.AuthViewModelFactory
import com.bangkit.eyetify.ui.viewmodel.model.LoginViewModel
import com.bangkit.eyetify.ui.viewmodel.model.MainViewModel
import com.bumptech.glide.Glide

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

        binding.profileCardAbout.setOnClickListener{
            showPopupAbout()
        }

        binding.profileCardLanguange.setOnClickListener{
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.profileCardRequirement.setOnClickListener{
            startActivity(Intent(requireContext(), RequirementActivity::class.java))
        }

        binding.profileCardPrivacy.setOnClickListener{
            startActivity(Intent(requireContext(), PrivacyActivity::class.java))
        }

        binding.cardLogout.setOnClickListener{
            showPopupLogout()
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

    private fun showPopupAbout() {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.pop_up_about_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnClose = dialog.findViewById<View>(R.id.button_close)

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showPopupLogout() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.pop_up_logout_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        val btnLogout = dialog.findViewById<Button>(R.id.btn_logout)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnLogout.setOnClickListener {
            viewModel.logout()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun loadUsername(): String? {
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }

    private fun loadEmail(): String? {
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", null)
    }

    private fun loadImage(): String?{
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("image", null)
    }

    private fun displayProfile() {
        val username = loadUsername()
        val email = loadEmail()
        val image = loadImage()

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

        if (image != null) {
            Glide.with(this).load(image).circleCrop().into(binding.imageProfile)
        } else {
            binding.imageProfile.setImageResource(R.drawable.placeholder)
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