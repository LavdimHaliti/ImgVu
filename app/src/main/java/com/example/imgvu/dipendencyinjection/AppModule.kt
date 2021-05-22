package com.example.imgvu.dipendencyinjection

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.imgvu.api.UnsplashApi
import com.example.imgvu.db.ImgVuDB
import com.example.imgvu.db.ImgVuDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * API/Retrofit Providers
     */
    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(UnsplashApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApi =
        retrofit.create(UnsplashApi::class.java)


    /**
     * Room Database Providers
     */
    @Provides
    fun provideImgVuDao(imgVuDB: ImgVuDB): ImgVuDao {
        return imgVuDB.imgVuDAO()
    }

    @Singleton
    @Provides
    fun provideImgVuDB(app: Application): ImgVuDB {
        return Room.databaseBuilder(
            app,
            ImgVuDB::class.java,
            "ImgVuDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}