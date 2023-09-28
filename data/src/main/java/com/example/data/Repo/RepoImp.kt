package com.example.data.Repo

import androidx.lifecycle.LiveData
import com.example.data.local.dao
import com.example.domain.entity.Translations
import com.example.domain.repo.translationRepo
import kotlinx.coroutines.flow.Flow

class RepoImp(private val dao: dao) : translationRepo {

    override suspend fun getAllTranslation(): Flow<List<Translations>> {
        return dao.getAllTranslation()
    }

    override suspend fun getAllSavedTranslation(): Flow<List<Translations>> {
        return dao.getAllSavedTranslation()
    }

    override suspend fun getLastTranslation(): Flow<Translations> {
        return dao.getLastTranslation()
    }

    override suspend fun getThreeLastTranslation(): Flow<List<Translations>> {
        return dao.getThreeLastTranslation()
    }

    override suspend fun getRandomTranslation(): Flow<List<Translations>> {
        return dao.getRandomTranslation()
    }

    override suspend fun addTranslation(translations: Translations) {
        dao.addTranslation(translations)
    }

    override suspend fun saveTranslation(translations: Translations) {
        dao.saveTranslation(translations)
    }

    override suspend fun deleteAllTranslation() {
        dao.deleteAllTranslation()
    }

    override suspend fun deleteOneTranslation(id: Int) {
        dao.deleteOneTranslation(id)
    }


}