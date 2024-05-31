package com.bangkit.eyetify.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EnsiklopediaModel (
    val name: String,
    val photo: Int,
    val description: String
) : Parcelable