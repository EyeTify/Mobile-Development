package com.bangkit.eyetify.data.response

import com.google.gson.annotations.SerializedName

data class FileUploadResponse(
	@SerializedName("data")
	val data: Data? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: String? = null
)

data class Data(
	@SerializedName("result")
	val result: String? = null,

	@SerializedName("createdAt")
	val createdAt: String? = null,

	@SerializedName("suggestion")
	val suggestion: String? = null,

	@SerializedName("id")
	val id: String? = null
)
