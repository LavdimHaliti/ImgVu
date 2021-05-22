package com.example.imgvu.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "image_table")
data class ImageDetail(

    @PrimaryKey(autoGenerate = true)
    var itemId: Int = 0,

    val blur_hash: String,
    val color: String,
    val created_at: String,
    val description: String?,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val urls: Urls,
    val user: User,
    val width: Int
) : Parcelable