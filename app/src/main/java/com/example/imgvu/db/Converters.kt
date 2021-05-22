package com.example.imgvu.db

import androidx.room.TypeConverter
import com.example.imgvu.data.ProfileImage
import com.example.imgvu.data.Urls
import com.example.imgvu.data.User

class Converters {

    @TypeConverter
    fun fromUrls(urls: Urls): String{
        return urls.regular
    }

    @TypeConverter
    fun toUrls(img: String): Urls{
        return Urls(img, img, img, img, img)
    }

    @TypeConverter
    fun fromUser(user: String?): User? {
        return user?.let { User(it,it,it,it,it,profile_image = ProfileImage(it,it,it),it,it,it) }
    }

    @TypeConverter
    fun toUser(user: User?): String? {
        return user?.username?.toString()
    }



}