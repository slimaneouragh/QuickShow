package com.example.remch.di

import android.content.Context
import com.example.data.Repo.RepoImp
import com.example.data.local.TranslationDatabase
import com.example.domain.entity.Translations
import com.example.domain.repo.translationRepo
import com.example.domain.usecase.DeleteAllTranslation
import com.example.domain.usecase.DeleteOneTranslation
import com.example.domain.usecase.GetAllSavedTranslation
import com.example.domain.usecase.GetLastTranslation
import com.example.domain.usecase.GetRandomTranslation
import com.example.domain.usecase.GetThreeLastTranslation
import com.example.domain.usecase.GetTranslation
import com.example.domain.usecase.InsertrTranslation
import com.example.domain.usecase.UpdateTranslation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModel {

    @Provides
    @Singleton
    fun provideGetAllUseCase(translationRepo: translationRepo): GetTranslation {
        return GetTranslation(translationRepo)
    }

    @Provides
    @Singleton
    fun provideGetAllSavedTranslation(translationsRepo: translationRepo) : GetAllSavedTranslation{
        return GetAllSavedTranslation(translationsRepo)
    }

    @Provides
    @Singleton
    fun provideInsertUseCase(translationRepo: translationRepo): InsertrTranslation {
        return InsertrTranslation(translationRepo)
    }

    @Provides
    @Singleton
    fun provideUpdateUseCase(translationRepo: translationRepo): UpdateTranslation {
        return UpdateTranslation(translationRepo)
    }

    @Provides
    @Singleton
    fun provideDeleteAllUseCase(translationRepo: translationRepo): DeleteAllTranslation {
        return DeleteAllTranslation(translationRepo)
    }

    @Provides
    @Singleton
    fun provideDeleteOneTranslation(translationRepo: translationRepo): DeleteOneTranslation {
        return DeleteOneTranslation(translationRepo)
    }

    @Provides
    @Singleton
    fun provideLastTranslationUseCase(translationRepo: translationRepo): GetLastTranslation {
        return GetLastTranslation(translationRepo)
    }

    @Provides
    @Singleton
    fun provideLastThreeTranslationUseCase(translationRepo: translationRepo): GetThreeLastTranslation {
        return GetThreeLastTranslation(translationRepo)
    }

    @Provides
    @Singleton
    fun provideRandomTranslation(translationRepo: translationRepo):GetRandomTranslation{
        return GetRandomTranslation(translationRepo)
    }



    @Provides
    @Singleton
    fun provideContext(@ApplicationContext app:Context): Context {
        return app
    }



}