package com.bangkit.eyetify.data.retrofit

import com.bangkit.eyetify.data.response.FileUploadResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ScanService {
    @Multipart
    @POST("upload-analyze")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): FileUploadResponse
}