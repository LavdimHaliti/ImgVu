package com.example.imgvu.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@kotlinx.parcelize.Parcelize
data class User(
    val first_name: String,
    val id: String,
    val instagram_username: String,
    val last_name: String,
    val name: String,
    val profile_image: ProfileImage,
    val portfolio_url: String,
    val twitter_username: String,
    val username: String
) : Parcelable{
    val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImgVu&utm_medium=referral"
}