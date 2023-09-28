package com.example.domain.repo

import com.example.domain.entity.Translations
import kotlinx.coroutines.flow.Flow

interface translationRepo {

   suspend fun getAllTranslation() : Flow<List<Translations>>
   suspend fun getAllSavedTranslation() : Flow<List<Translations>>
   suspend fun getLastTranslation() : Flow<Translations>
   suspend fun getThreeLastTranslation() : Flow<List<Translations>>
   suspend fun getRandomTranslation() : Flow<List<Translations>>
   suspend fun addTranslation(translations: Translations)
   suspend fun saveTranslation(translations: Translations)
   suspend fun deleteAllTranslation()
   suspend fun deleteOneTranslation(id: Int)


}