package com.example.remch.di

import android.app.Application
import android.content.Context
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Dao
import androidx.room.Room
import com.example.data.Repo.RepoImp
import com.example.data.local.TranslationDatabase
import com.example.data.local.dao
import com.example.domain.entity.Translations
import com.example.domain.repo.translationRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModel {

    @Provides
    @Singleton
    fun provideTranslationDataBase(@ApplicationContext app:Context): TranslationDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            TranslationDatabase::class.java,
            "translationDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }






}