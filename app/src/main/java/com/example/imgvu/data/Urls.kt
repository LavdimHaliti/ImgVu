package com.example.imgvu.data

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
) : Parcelable