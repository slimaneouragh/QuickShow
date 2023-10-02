package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.domain.entity.Translations

@Database(entities = [Translations::class], version = 1, exportSchema = false)
abstract class TranslationDatabase():RoomDatabase(){
abstract fun translationDao() : dao
    companion object {
        @Volatile
        private var Instance:TranslationDatabase? = null
        fun getDatabase(context: Context): TranslationDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context = context,TranslationDatabase::class.java,"translationDatabase")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        Instance = it
                    }

            }

        }
    }
}
