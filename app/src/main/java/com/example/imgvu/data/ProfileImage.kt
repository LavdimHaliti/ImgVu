package com.example.imgvu.data

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
) : Parcelable