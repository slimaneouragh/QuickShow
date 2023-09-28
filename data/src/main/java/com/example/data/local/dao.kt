package com.example.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.DeleteTable
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.domain.entity.Translations
import kotlinx.coroutines.flow.Flow


@Dao
interface dao {

    @Upsert()
    suspend fun addTranslation(translation: Translations)

    @Update()
    suspend fun saveTranslation(translation: Translations)

    @Query("SELECT * from translation ORDER BY `id` ASC")
    fun getAllTranslation(): Flow<List<Translations>>

    @Query("SELECT * from translation where saved = 1 ORDER BY `id` ASC")
    fun getAllSavedTranslation(): Flow<List<Translations>>

    @Query("SELECT * FROM translation ORDER BY id DESC LIMIT 1")
    fun getLastTranslation():Flow<Translations>

    @Query("SELECT * FROM translation ORDER BY id DESC LIMIT 3")
    fun getThreeLastTranslation():Flow<List<Translations>>

    @Query("SELECT * FROM translation ORDER BY RANDOM() LIMIT 3")
    fun getRandomTranslation():Flow<List<Translations>>

    @Query("DELETE FROM translation")
    suspend fun deleteAllTranslation()

//    @Delete()
    @Query("DELETE FROM translation where id = :id")
    fun deleteOneTranslation(id: Int)




}