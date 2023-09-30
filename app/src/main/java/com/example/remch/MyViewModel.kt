package com.example.remch

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.domain.entity.Translations
import com.example.domain.usecase.DeleteAllTranslation
import com.example.domain.usecase.DeleteOneTranslation
import com.example.domain.usecase.GetAllSavedTranslation
import com.example.domain.usecase.GetLastTranslation
import com.example.domain.usecase.GetRandomTranslation
import com.example.domain.usecase.GetThreeLastTranslation
import com.example.domain.usecase.GetTranslation
import com.example.domain.usecase.InsertrTranslation
import com.example.domain.usecase.UpdateTranslation
import com.example.remch.popUp.PreferenceStorage
import com.example.remch.popUp.Worker
import com.example.remch.utils.TypesOfFetchData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val context: Context,
    private val GetTranslation: GetTranslation,
    private val InsertTranslation: InsertrTranslation,
    private val UpdateTranslation: UpdateTranslation,
    private val DeleteTranslation: DeleteAllTranslation,
    private val DeleteOneTranslation: DeleteOneTranslation,
    private val GetLastTranslation: GetLastTranslation,
    private val GetThreeLastTranslation: GetThreeLastTranslation,
    private val GetRandomTranslation: GetRandomTranslation,
    private val GetAllSavedTranslation: GetAllSavedTranslation,
) : ViewModel() {

    val worker: WorkManager = WorkManager.getInstance(context)

    val dataStore = PreferenceStorage(context)

    val _listLanguage = MutableStateFlow(
        listOf<String>(
            "AFRIKAANS",
            "ALBANIAN",
            "ARABIC",
            "BELARUSIAN",
            "BULGARIAN",
            "BENGALI",
            "CATALAN",
            "CHINESE",
            "CROATIAN",
            "CZECH",
            "DANISH",
            "DUTCH",
            "ENGLISH",
            "ESPERANTO",
            "ESTONIAN",
            "FINNISH",
            "FRENCH",
            "GALICIAN",
            "GEORGIAN",
            "GERMAN",
            "GREEK",
            "GUJARATI",
            "HAITIAN_CREOLE",
            "HEBREW",
            "HINDI",
            "HUNGARIAN",
            "ICELANDIC",
            "INDONESIAN",
            "IRISH",
            "ITALIAN",
            "JAPANESE",
            "KANNADA",
            "KOREAN",
            "LITHUANIAN",
            "LATVIAN",
            "MACEDONIAN",
            "MARATHI",
            "MALAY",
            "MALTESE",
            "NORWEGIAN",
            "PERSIAN",
            "POLISH",
            "PORTUGUESE",
            "ROMANIAN",
            "RUSSIAN",
            "SLOVAK",
            "SLOVENIAN",
            "SPANISH",
            "SWEDISH",
            " SWAHILI ",
            "TAGALOGTAMIL",
            " TELUGU",
            "THAI ",
            " TURKISH",
            "UKRAINIAN ",
            "URDU ",
            "VIETNAMESE",
            " WELSH "
        )
    )
    val listLanguage = _listLanguage.asStateFlow()

    val _timeOfRepetition: MutableStateFlow<Int> = MutableStateFlow(15)
    val getTime: StateFlow<Int> = _timeOfRepetition

    val _PopUpState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val getState: StateFlow<Boolean> = _PopUpState

    val list_State: MutableStateFlow<List<Translations?>> = MutableStateFlow(emptyList())
    val first_launch: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val list_StateSaved: MutableStateFlow<List<Translations?>> = MutableStateFlow(emptyList())
    val first_launchSaved: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val _detectedText: MutableStateFlow<String> = MutableStateFlow("")
    val detectedText: StateFlow<String> = _detectedText

    private val _getAll: MutableStateFlow<Flow<List<Translations?>>> = MutableStateFlow(emptyFlow())
    val getAll: StateFlow<Flow<List<Translations?>>> = _getAll

    private val _getAllSaved: MutableStateFlow<Flow<List<Translations?>>> =
        MutableStateFlow(emptyFlow())
    val getAllSaved: StateFlow<Flow<List<Translations?>>> = _getAllSaved

    private val _getLast: MutableStateFlow<Flow<Translations?>> = MutableStateFlow(emptyFlow())
    val getLast: StateFlow<Flow<Translations?>> = _getLast

    private val _getLastThree: MutableStateFlow<Flow<List<Translations?>>> =
        MutableStateFlow(emptyFlow())
    val getLastThree: StateFlow<Flow<List<Translations?>>> = _getLastThree

    private val _getRandomly: MutableStateFlow<Flow<List<Translations?>>> =
        MutableStateFlow(emptyFlow())
    val getRandomly: StateFlow<Flow<List<Translations?>>> = _getRandomly

    private val _triggerSnackBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val triggerSnackBar: StateFlow<Boolean> = _triggerSnackBar

    private val _fetchType: MutableStateFlow<String> = MutableStateFlow("")
    val fetchType: StateFlow<String> = _fetchType


    fun addTranslation(translations: Translations) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                InsertTranslation(translations)

            } catch (e: Exception) {

            }
        }
    }


    fun deleteOneTranslation(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                DeleteOneTranslation(id)
//                _getAll.value = GetTranslation()
            } catch (e: Exception) {


            }
        }
    }

    fun updateTranslation(translations: Translations) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                UpdateTranslation(translations)

            } catch (e: Exception) {

            }

        }
    }

    fun getAllSavedTranslation() {
        viewModelScope.launch {
            try {
                _getAllSaved.value = GetAllSavedTranslation()
            } catch (e: Exception) {

            }
        }
    }

    fun getAllTranslation() {

        viewModelScope.launch {
            try {
                _getAll.value = GetTranslation()
            } catch (e: Exception) {

            }

        }
    }


    fun getLastTranslation() {

        viewModelScope.launch {
            try {
                _getLast.value = GetLastTranslation()
            } catch (e: Exception) {

            }

        }
    }

    fun getThreeLastTranslation() {

        viewModelScope.launch {
            try {
                _getLastThree.value = GetThreeLastTranslation()
            } catch (e: Exception) {

            }
        }
    }

    fun getRandomTranslation() {

        viewModelScope.launch {
            try {
                _getRandomly.value = GetRandomTranslation()
            } catch (e: Exception) {

            }
        }
    }

    fun deleteAllTranslation() {

        viewModelScope.launch {
            try {
                DeleteTranslation()
            } catch (e: Exception) {

            }

        }
    }

    fun changeState(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataStore.changeState(state)
            } catch (e: Exception) {

            }
        }
    }

    fun getState() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataStore.getState.collect {

                    it?.let {
                        _PopUpState.value = it
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    fun changeTime(time: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataStore.changeTime(time)
            } catch (e: Exception) {

            }
        }
    }

    fun getTime() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataStore.getTime.collect {
                    it?.let {
                        _timeOfRepetition.value = it
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    fun getFetchType() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataStore.getFetchDataType.collect {
                    it?.let {
                        _fetchType.value = it
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    fun changeFetchDataType(type: TypesOfFetchData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataStore.changeFetchDataType(type)
            } catch (e: Exception) {

            }
        }
    }

    fun updateText(detectedText: String) {
        viewModelScope.launch {
            try {
                _detectedText.value = detectedText
            } catch (e: Exception) {

            }
        }
    }

    fun updateTriggerSnackBar(state: Boolean) {
        viewModelScope.launch {
            try {
                _triggerSnackBar.value = state
            } catch (e: Exception) {

            }
        }
    }


    fun cancelAllWorkers() {
        worker.cancelAllWork()

    }


    suspend fun launchWorker(
        pickerValueHours: Int,
        pickerValueMinutes: Int,
        type: TypesOfFetchData,
        context: Context
    ) {

        val listOfTranslation: ArrayList<String> = arrayListOf<String>()

        when (type) {
            TypesOfFetchData.RANDOMLY -> {
                getRandomTranslation()

                getRandomly.value.collect {
                    if (it.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Make Any Translation before to Activate this Option",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {

                        listOfTranslation.clear()

                        for (t in 0 until (it.size)) {

                            listOfTranslation.add(it[t]!!.textFrom)
                            listOfTranslation.add(it[t]!!.textTo)
                            listOfTranslation.add(it[t]!!.from)
                            listOfTranslation.add(it[t]!!.to)

                        }
                        worker.cancelAllWork()

                        val taskData =
                            Data.Builder().putStringArray(
                                "Text",
                                listOfTranslation.toTypedArray() as Array<out String>
                            ).build()
                        val request = PeriodicWorkRequestBuilder<Worker>(
                            ((pickerValueHours * 60) + pickerValueMinutes).toLong(),
                            TimeUnit.MINUTES
                        ).setInputData(taskData).build()
                        worker.enqueue(request)


                    }

                }


            }

            TypesOfFetchData.LAST_THREE -> {
                getThreeLastTranslation()
                getLastThree.value.collect {

                    if (it.isEmpty()) {

                        updateTriggerSnackBar(true)

                    } else {

                        listOfTranslation.clear()

                        for (t in 0 until (it.size)) {

                            listOfTranslation.add(it[t]!!.textFrom)
                            listOfTranslation.add(it[t]!!.textTo)
                            listOfTranslation.add(it[t]!!.from)
                            listOfTranslation.add(it[t]!!.to)

                        }
                        worker.cancelAllWork()

                        val taskData =
                            Data.Builder().putStringArray(
                                "Text",
                                listOfTranslation.toTypedArray() as Array<out String>
                            ).build()
                        val request = PeriodicWorkRequestBuilder<Worker>(
                            ((pickerValueHours * 60) + pickerValueMinutes).toLong(),
                            TimeUnit.MINUTES
                        ).setInputData(taskData).build()
                        worker.enqueue(request)


                    }
                }
            }

            TypesOfFetchData.SAVED -> {
                getAllSavedTranslation()

                getAllSaved.value.collect {

                    if (it.isEmpty()) {

                        updateTriggerSnackBar(true)

                    } else {

                        listOfTranslation.clear()

                        for (t in 0 until (it.size)) {

                            listOfTranslation.add(it[t]!!.textFrom)
                            listOfTranslation.add(it[t]!!.textTo)
                            listOfTranslation.add(it[t]!!.from)
                            listOfTranslation.add(it[t]!!.to)

                        }
                        worker.cancelAllWork()

                        val taskData =
                            Data.Builder().putStringArray(
                                "Text",
                                listOfTranslation.toTypedArray() as Array<out String>
                            ).build()
                        val request = PeriodicWorkRequestBuilder<Worker>(
                            ((pickerValueHours * 60) + pickerValueMinutes).toLong(),
                            TimeUnit.MINUTES
                        ).setInputData(taskData).build()
                        worker.enqueue(request)


                    }
                }
            }
        }


    }

    suspend fun launchWorker(
        totalTimeInMinutes: Int = 0,
        type: String,
        context: Context
    ) {

        val listOfTranslation: ArrayList<String> = arrayListOf<String>()

        when (type) {
            TypesOfFetchData.RANDOMLY.name -> {
                getRandomTranslation()

                getRandomly.value.collect {
                    if (it.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Make Any Translation before to Activate this Option",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {

                        listOfTranslation.clear()

                        for (t in 0 until (it.size)) {

                            listOfTranslation.add(it[t]!!.textFrom)
                            listOfTranslation.add(it[t]!!.textTo)
                            listOfTranslation.add(it[t]!!.from)
                            listOfTranslation.add(it[t]!!.to)

                        }
                        worker.cancelAllWork()

                        val taskData =
                            Data.Builder().putStringArray(
                                "Text",
                                listOfTranslation.toTypedArray() as Array<out String>
                            ).build()
                        val request = PeriodicWorkRequestBuilder<Worker>(
                            totalTimeInMinutes.toLong(),
                            TimeUnit.MINUTES
                        ).setInputData(taskData).build()
                        worker.enqueue(request)


                    }

                }


            }

            TypesOfFetchData.LAST_THREE.name -> {
                getThreeLastTranslation()
                getLastThree.value.collect {

                    if (it.isEmpty()) {

                        updateTriggerSnackBar(true)

                    } else {

                        listOfTranslation.clear()

                        for (t in 0 until (it.size)) {

                            listOfTranslation.add(it[t]!!.textFrom)
                            listOfTranslation.add(it[t]!!.textTo)
                            listOfTranslation.add(it[t]!!.from)
                            listOfTranslation.add(it[t]!!.to)

                        }
                        worker.cancelAllWork()

                        val taskData =
                            Data.Builder().putStringArray(
                                "Text",
                                listOfTranslation.toTypedArray() as Array<out String>
                            ).build()
                        val request = PeriodicWorkRequestBuilder<Worker>(
                            totalTimeInMinutes.toLong(),
                            TimeUnit.MINUTES
                        ).setInputData(taskData).build()
                        worker.enqueue(request)


                    }
                }
            }

            TypesOfFetchData.SAVED.name -> {

                getAllSavedTranslation()

                getAllSaved.value.collect {

                    if (it.isEmpty()) {

                        updateTriggerSnackBar(true)

                    } else {

                        listOfTranslation.clear()

                        for (t in 0 until (it.size)) {

                            listOfTranslation.add(it[t]!!.textFrom)
                            listOfTranslation.add(it[t]!!.textTo)
                            listOfTranslation.add(it[t]!!.from)
                            listOfTranslation.add(it[t]!!.to)

                        }
                        worker.cancelAllWork()

                        val taskData =
                            Data.Builder().putStringArray(
                                "Text",
                                listOfTranslation.toTypedArray() as Array<out String>
                            ).build()
                        val request = PeriodicWorkRequestBuilder<Worker>(
                            totalTimeInMinutes.toLong(),
                            TimeUnit.MINUTES
                        ).setInputData(taskData).build()
                        worker.enqueue(request)


                    }
                }
            }
        }


    }


}