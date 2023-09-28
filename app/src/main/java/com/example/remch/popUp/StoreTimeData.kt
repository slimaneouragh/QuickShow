package com.example.remch.popUp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.remch.utils.TypesOfFetchData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceStorage(private val context: Context) {

    companion object{
        private val  Context.dataStore : DataStore<Preferences> by preferencesDataStore("app_data")

        val TIMER_STATE_KEY = booleanPreferencesKey("User_State_LogIn")
        val TIME_KEY = intPreferencesKey("Counter")
        val FETCH_DATA_TYPE = stringPreferencesKey("Type of fetch Data")


    }


    val getState : Flow<Boolean?> = context.dataStore.data.map {preferences ->
        preferences[TIMER_STATE_KEY] ?: false
    }
    val getTime : Flow<Int?> = context.dataStore.data.map {preferences ->
        preferences[TIME_KEY] ?: 0
    }
    val getFetchDataType : Flow<String?> = context.dataStore.data.map {preferences ->
        preferences[FETCH_DATA_TYPE] ?:"RANDOMLY"
    }




    suspend fun changeTime(approve:Int){
        context.dataStore.edit { preferences ->
            preferences[TIME_KEY] = approve
        }
    }
    suspend fun changeState(state: Boolean){
        context.dataStore.edit { preferences ->
            preferences[TIMER_STATE_KEY] = state
        }
    }
    suspend fun changeFetchDataType(type: TypesOfFetchData){
        context.dataStore.edit { preferences ->
            preferences[FETCH_DATA_TYPE] = type.name
        }
    }


}