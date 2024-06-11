package com.bangkit.eyetify.ui.fragment

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bangkit.eyetify.R
import com.bangkit.eyetify.data.response.FileUploadResponse
import com.bangkit.eyetify.data.retrofit.ScanConfig
import com.bangkit.eyetify.data.utils.reduceFileImage
import com.bangkit.eyetify.data.utils.uriToFile
import com.bangkit.eyetify.databinding.FragmentScanBinding
import com.bangkit.eyetify.ui.activity.ResultActivity
import com.bangkit.eyetify.ui.viewmodel.factory.AuthViewModelFactory
import com.bangkit.eyetify.ui.viewmodel.model.MainViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get()= _binding!!

    private var currentImageUri: Uri? = null

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
            Log.d("ScanFragment", "Add Image Button Clicked")
            showPopup()
        }

        binding.analyzeImageButton.setOnClickListener{
            uploadImage()
        }
    }



    private fun showPopup() {
        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.pop_up_action_dialog)
        dialog.show()

        val btnCamera = dialog.findViewById<Button>(R.id.camera_btn)
        val btnGalery = dialog.findViewById<Button>(R.id.gallery_btn)

        btnCamera.setOnClickListener {
            startGallery()
            dialog.dismiss()
        }

        btnGalery.setOnClickListener {
            startGallery()
            dialog.dismiss()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imagePlaceholder.setImageURI(it)
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            Log.d("Image Classification File", "File path: ${imageFile.path}")
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )
            lifecycleScope.launch {
                try {
                    val apiService = ScanConfig.getApiService()
                    val response = apiService.uploadImage(multipartBody)

                    response.data?.let { data ->
                        // Tampilkan hasil
                        binding.textTitleUpload.text = run {
                            data.result?.let { showToast(it) }
                            data.result
                        }

                        binding.textDescriptionUpload.text = run {
                            data.suggestion?.let { showToast(it) }
                            data.suggestion
                        }

                        // Arahkan ke ResultActivity dan sertakan hasil analisis
                        val intent = Intent(requireContext(), ResultActivity::class.java)
                        intent.putExtra("imageExtra", currentImageUri.toString())
                        intent.putExtra("result", data.result)
                        intent.putExtra("suggestion", data.suggestion)
                        startActivity(intent)
                        requireActivity().finish() // Optional, menutup activity saat ini jika perlu
                    } ?: showToast("No data received from the server.")

                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
                    showToast(errorResponse.message.toString())
                } catch (e: Exception) {
                    showToast("An unexpected error occurred.")
                    Log.e("Upload Error", "Error: ${e.message}", e)
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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