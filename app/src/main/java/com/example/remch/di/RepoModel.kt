package com.example.remch.di

import com.example.data.Repo.RepoImp
import com.example.data.local.TranslationDatabase
import com.example.data.local.dao
import com.example.domain.repo.translationRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModel {
    @Provides
    @Singleton
    fun provideRepo(db: TranslationDatabase): translationRepo {
        return RepoImp(db.translationDao())
    }
}