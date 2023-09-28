package com.example.remch.view.exchange_Screen

import android.annotation.SuppressLint
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.repeatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.domain.entity.Translations
import com.example.remch.MainActivity
import com.example.remch.MyViewModel
import com.example.remch.R
import com.example.remch.Window
import com.example.remch.popUp.CustomOverlayView
import com.example.remch.popUp.Worker
import com.example.remch.ui.theme.apple_font
import com.example.remch.ui.theme.dosis_font
import com.example.remch.utils.TypesOfFetchData
import com.exyte.animatednavbar.utils.noRippleClickable
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.TimeUnit

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Exchange(viewModel: MyViewModel) {
    val focusManager = LocalFocusManager.current
    val listLanguageWithTAG = TranslateLanguage.getAllLanguages()
    val listLanguage = viewModel.listLanguage.collectAsStateWithLifecycle().value
    var fetchDataType = viewModel.fetchType.collectAsStateWithLifecycle()


    var co by remember { mutableStateOf(false) }

    var toLanguageIndex by remember { mutableStateOf(7) }

    var fromLanguageIndex by remember { mutableStateOf(12) }
    var medianLanguageIndex by remember { mutableStateOf(0) }

    var shoseFromLanguage by remember { mutableStateOf(false) }
    var shoseToLanguage by remember { mutableStateOf(false) }


    viewModel.getState()
    viewModel.getTime()

    val lastThreeTranslation =
        viewModel.getLastThree.value.collectAsState(initial = emptyList())
    val timerState =
        viewModel.getState.collectAsStateWithLifecycle()
    val timerValue =
        viewModel.getTime.collectAsStateWithLifecycle()


    viewModel.getThreeLastTranslation()
    viewModel.getLastTranslation()


    val lastTrans = viewModel.getLast.value.collectAsStateWithLifecycle(
        initialValue = null
    )
    val lastTranslation = remember(lastTrans.value) { derivedStateOf { lastTrans.value } }


    var fromLanguage by remember { mutableStateOf(listLanguage[12]) }
    var toLanguage by remember { mutableStateOf(listLanguage[7]) }
    var medianLanguage by remember { mutableStateOf(listLanguage[12]) }
    lastTranslation.value?.let {
        if (!co) {
            fromLanguage = it.from
            toLanguage = it.to
            co = true
            fromLanguageIndex = listLanguage.indexOf(it.from)
            toLanguageIndex = listLanguage.indexOf(it.to)
        }

    }
    var fromTextState by remember { mutableStateOf("") }
    var toTextState by remember { mutableStateOf("") }
    var medianTextState by remember { mutableStateOf("") }


    val context = LocalContext.current


    val option = TranslatorOptions.Builder()
        .setSourceLanguage(listLanguageWithTAG[fromLanguageIndex])
        .setTargetLanguage(listLanguageWithTAG[toLanguageIndex])
        .build()

    val englishToarabicTranslation = Translation.getClient(option)




    englishToarabicTranslation.downloadModelIfNeeded()
        .addOnSuccessListener {
//            Log.d("Tag", "SuccessDownloaded")
        }.addOnFailureListener {
//            Log.d("Tag", "FailureDownloaded")

        }

    if (fromTextState.isNotEmpty()) {
        englishToarabicTranslation.translate(fromTextState)
            .addOnSuccessListener {
                toTextState = it
            }
            .addOnFailureListener {
                Log.d("Tag", "FailureTranslation")

            }

    } else if (fromTextState.isEmpty()) {
        toTextState = ""

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {


        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isSystemInDarkTheme()) {
                        Color(27, 27, 27, 255)
                    } else {
                        Color(220, 220, 220, 255)
                    }
                )
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .align(Alignment.Center),
//
                )

                IconButton(modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 10.dp), onClick = {


                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.profile_circle_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }


            }


            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Box {

                        Text(
                            text =
                            fromLanguage
                                .lowercase()
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier
                                .align(
                                    Alignment.Center
                                )
                                .noRippleClickable {
                                    shoseToLanguage = false
                                    shoseFromLanguage = true
                                },
                        )


                    }

                    IconButton(onClick = {
                        medianLanguage = toLanguage
                        toLanguage = fromLanguage
                        fromLanguage = medianLanguage

                        medianLanguageIndex = toLanguageIndex
                        toLanguageIndex = fromLanguageIndex
                        fromLanguageIndex = medianLanguageIndex

                        medianTextState = toTextState
                        toTextState = fromTextState
                        fromTextState = medianTextState

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.exchange_svgrepo_com),
                            contentDescription = ""
                        )
                    }

                    Box {

                        Text(
                            text =
                            toLanguage
                                .lowercase()
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier
                                .align(
                                    Alignment.Center
                                )
                                .noRippleClickable {
                                    shoseFromLanguage = false
                                    shoseToLanguage = true
                                },
                        )


                    }

                }

                OutlinedTextField(
                    value = fromTextState,
                    onValueChange = {
                        fromTextState = it
                        if (fromTextState.isNotEmpty()) {
                            englishToarabicTranslation.translate(fromTextState)
                                .addOnSuccessListener {
                                    toTextState = it
                                }
                                .addOnFailureListener {
                                    Log.d("Tag", "FailureTranslation")

                                }

                        } else if (fromTextState.isEmpty()) {
                            toTextState = ""

                        }


                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(200.dp)
                        .noRippleClickable {
                            if (shoseFromLanguage || shoseToLanguage) {
                                shoseFromLanguage = false
                                shoseToLanguage = false
                            }

                        },
                    textStyle = TextStyle.Default.copy(
                        fontSize = 17.sp,
                        fontFamily = apple_font,
                        lineHeight = 20.sp,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        ),
                        textDirection = TextDirection.Ltr
                    ),
                    shape = RoundedCornerShape(20.dp),
                    label = {

                        Text(
                            text =
                            fromLanguage
                                .lowercase()
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    trailingIcon = {
                        Box(modifier = Modifier.fillMaxHeight()) {
                            Icon(
                                ImageVector.vectorResource(id = R.drawable.x_svgrepo_com),
                                contentDescription = "",
                                tint = Color.Black,
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 20.dp)
                                    .noRippleClickable {
                                        if (toTextState.isNotEmpty() && fromTextState.isNotEmpty()) {


//                                            viewModel.getThreeLastTranslation()
//                                            if (timerState.value) {
//
//                                                lastThreeTranslation.value?.let {
//                                                    worker = WorkManager.getInstance(context)
//                                                    worker.cancelAllWork()
//
//                                                    val listOfTranslation: ArrayList<String> =
//                                                        arrayListOf<String>()
//                                                    for (t in 0 until (it.size)) {
//
//                                                        listOfTranslation.add(it[t]!!.textFrom)
//                                                        listOfTranslation.add(it[t]!!.textTo)
//                                                        listOfTranslation.add(it[t]!!.from)
//                                                        listOfTranslation.add(it[t]!!.to)
//
//                                                    }
//                                                    val taskData =
//                                                        Data
//                                                            .Builder()
//                                                            .putStringArray(
//                                                                "Text",
//                                                                listOfTranslation.toTypedArray() as Array<out String>
//                                                            )
//                                                            .build()
//                                                    val request =
//                                                        PeriodicWorkRequestBuilder<Worker>(
//                                                            timerValue.value.toLong(),
//                                                            TimeUnit.MINUTES
//                                                        )
//                                                            .setInputData(taskData)
//                                                            .build()
//
//                                                    worker
//                                                        .getWorkInfoByIdLiveData(request.id)
//                                                        .observe(
//                                                            MainActivity(),
//                                                            androidx.lifecycle.Observer { workInfo ->
//                                                                workInfo.let {
//
//
//                                                                }
//                                                            })
//
//                                                    worker.enqueue(request)
//
//                                                }
//                                            }
                                            if (
                                                lastTranslation.value!!.textFrom != fromTextState
                                                &&
                                                lastTranslation.value!!.textTo != toTextState
                                            ) {

                                                viewModel.addTranslation(
                                                    Translations(
                                                        from = fromLanguage,
                                                        to = toLanguage,
                                                        textFrom = fromTextState,
                                                        textTo = toTextState
                                                    )
                                                )

                                                viewModel.viewModelScope.launch {
                                                    viewModel.launchWorker(
                                                        totalTimeInMinutes = timerValue.value,
                                                        type = when (fetchDataType.value) {
                                                            TypesOfFetchData.RANDOMLY.name -> {
                                                                TypesOfFetchData.RANDOMLY.name
                                                            }

                                                            TypesOfFetchData.LAST_THREE.name -> {
                                                                TypesOfFetchData.LAST_THREE.name
                                                            }

                                                            TypesOfFetchData.HYBRID.name -> {
                                                                TypesOfFetchData.HYBRID.name
                                                            }

                                                            else -> {
                                                                TypesOfFetchData.RANDOMLY.name
                                                            }
                                                        },
                                                        context = context
                                                    )
                                                }

                                            }

                                            if (lastTranslation.value == null) {
                                                viewModel.addTranslation(
                                                    Translations(
                                                        from = fromLanguage,
                                                        to = toLanguage,
                                                        textFrom = fromTextState,
                                                        textTo = toTextState
                                                    )
                                                )
                                            }

                                        } else {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Please Write Something",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        }
                                        fromTextState = ""
                                        toTextState = ""
                                    }
                            )
                        }

                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {


                            if (toTextState.isNotEmpty() && fromTextState.isNotEmpty()) {

                                viewModel.getLastTranslation()
                                lastTranslation.value?.let {

                                    if (
                                        lastTranslation.value!!.textFrom != fromTextState
                                        &&
                                        lastTranslation.value!!.textTo != toTextState
                                    ) {
                                        viewModel.addTranslation(
                                            Translations(
                                                from = fromLanguage,
                                                to = toLanguage,
                                                textFrom = fromTextState,
                                                textTo = toTextState
                                            )
                                        )

                                        Toast.makeText(context,fetchDataType.value,Toast.LENGTH_LONG).show()
                                        viewModel.viewModelScope.launch {
                                            viewModel.launchWorker(
                                                totalTimeInMinutes = timerValue.value,
                                                type =
                                                when (fetchDataType.value) {
                                                    TypesOfFetchData.RANDOMLY.name -> {
                                                        TypesOfFetchData.RANDOMLY.name
                                                    }

                                                    TypesOfFetchData.LAST_THREE.name -> {
                                                        TypesOfFetchData.LAST_THREE.name
                                                    }

                                                    TypesOfFetchData.HYBRID.name -> {
                                                        TypesOfFetchData.HYBRID.name
                                                    }

                                                    else -> {
                                                        TypesOfFetchData.RANDOMLY.name
                                                    }
                                                }
                                                ,
                                                context = context
                                            )
                                        }

                                    }
                                }

                                if (lastTranslation.value == null) {
                                    viewModel.addTranslation(
                                        Translations(
                                            from = fromLanguage,
                                            to = toLanguage,
                                            textFrom = fromTextState,
                                            textTo = toTextState
                                        )
                                    )
                                }


//
//                                viewModel.getThreeLastTranslation()
//                                if (timerState.value) {
//
//                                    lastThreeTranslation.value?.let {
//                                        worker = WorkManager.getInstance(context)
//                                        worker.cancelAllWork()
//
//                                        val listOfTranslation: ArrayList<String> =
//                                            arrayListOf<String>()
//                                        for (t in 0 until (it.size)) {
//
//                                            listOfTranslation.add(it[t]!!.textFrom)
//                                            listOfTranslation.add(it[t]!!.textTo)
//                                            listOfTranslation.add(it[t]!!.from)
//                                            listOfTranslation.add(it[t]!!.to)
//
//                                        }
//                                        val taskData =
//                                            Data.Builder().putStringArray(
//                                                "Text",
//                                                listOfTranslation.toTypedArray() as Array<out String>
//                                            ).build()
//                                        val request = PeriodicWorkRequestBuilder<Worker>(
//                                            timerValue.value.toLong(),
//                                            TimeUnit.MINUTES
//                                        ).setInputData(taskData).build()
//
//                                        worker.getWorkInfoByIdLiveData(request.id)
//                                            .observe(
//                                                MainActivity(),
//                                                androidx.lifecycle.Observer { workInfo ->
//                                                    workInfo.let {
//
//
//                                                    }
//                                                })
//
//                                        worker.enqueue(request)
//
//                                    }
//                                }
//
//                                viewModel.addTranslation(
//                                    Translations(
//                                        from = fromLanguage,
//                                        to = toLanguage,
//                                        textFrom = fromTextState,
//                                        textTo = toTextState
//                                    )
//                                )

                            }
                            focusManager.clearFocus()
                        }
                    )

                )

                OutlinedTextField(
                    value = toTextState,
                    onValueChange = { toTextState = it },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(200.dp)
                        .noRippleClickable {
                            if (shoseFromLanguage || shoseToLanguage) {
                                shoseFromLanguage = false
                                shoseToLanguage = false
                            }

                        },
                    shape = RoundedCornerShape(20.dp),
                    textStyle = TextStyle.Default.copy(
                        fontSize = 17.sp,
                        fontFamily = apple_font,
                        lineHeight = 20.sp,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        ),
                        textDirection = TextDirection.Rtl
                    ),
                    label = {
                        Text(
                            text =
                            toLanguage
                                .lowercase()
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                )
            }

            Divider(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 10.dp)
                    .fillMaxWidth(0.8f)
            )




            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 5.dp)
            ) {

                Text(
                    text = "History",
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(bottom = 10.dp),
                    style = MaterialTheme.typography.bodySmall
                )


                LazyColumn(
                    modifier = Modifier,
                    content = {


                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .border(
                                        1.dp,
                                        if (isSystemInDarkTheme()) {
                                            Color(228, 226, 226, 100)
                                        } else {
                                            Color(22, 22, 22, 100)
                                        }, RoundedCornerShape(20.dp)
                                    )
                            ) {

                                Column(
                                    verticalArrangement = Arrangement.SpaceAround,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.align(Alignment.Center)
                                ) {

                                    lastTranslation.value?.let {
                                        Row(
                                            modifier = Modifier
//                                        .fillMaxWidth(0.5f),
                                            ,
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = it.from,
                                                color = Color(22, 22, 22, 255),
                                                modifier = Modifier

                                                    .padding(10.dp),
                                                fontFamily = dosis_font[0],
                                                fontSize = 12.sp
                                            )

                                            Icon(
                                                ImageVector.vectorResource(id = R.drawable.exchange_svgrepo_com),
                                                contentDescription = ""
                                            )

                                            Text(
                                                text = it.to,
                                                color = Color(22, 22, 22, 255),
                                                modifier = Modifier

                                                    .padding(10.dp),

                                                fontFamily = dosis_font[0],
                                                fontSize = 12.sp
                                            )


                                        }

                                        Divider(
                                            modifier = Modifier.fillMaxWidth(0.9f),
                                            color = Color(124, 124, 124, 188)
                                        )

                                        Column(
                                            verticalArrangement = Arrangement.SpaceAround,
                                            horizontalAlignment = Alignment.Start,
                                        ) {
                                            Text(
                                                text = it.textFrom,
                                                color = Color(22, 22, 22, 255),
                                                modifier = Modifier

                                                    .padding(10.dp),
                                                fontFamily = dosis_font[0],
                                                fontSize = 15.sp
                                            )

                                            Text(
                                                text = it.textTo,
                                                color = Color(22, 22, 22, 255),
                                                modifier = Modifier

                                                    .padding(10.dp),
                                                fontFamily = dosis_font[0],
                                                fontSize = 15.sp
                                            )

                                        }

                                    }
                                }

                            }
                        }

                    })
            }


        }



        if (shoseFromLanguage) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(1, 1, 1, 104))
                    .noRippleClickable {
                        if (shoseFromLanguage || shoseToLanguage) {
                            shoseFromLanguage = false
                            shoseToLanguage = false
//
                        }

                    }) {

                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .fillMaxHeight(0.45f)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.White)
                        .align(Alignment.Center)
                ) {

                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {


                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = stringResource(id = R.string.Chose_your_Language),
                                color = Color(22, 22, 22, 255),
                                modifier = Modifier
                                    .padding(10.dp),
                                fontFamily = dosis_font[0],
                                fontSize = 15.sp,
                            )


                        }

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(
                                    top = 10.dp,
                                    bottom = 10.dp
                                ),
                            color = Color(124, 124, 124, 188)
                        )

                        LazyColumn(
                            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally, content = {
                                items(listLanguage) {
                                    Text(
                                        text = it.lowercase().replaceFirstChar {
                                            if (it.isLowerCase()) it.titlecase() else it.toString()
                                        },
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .noRippleClickable {
                                                fromLanguage = it
                                                fromLanguageIndex = listLanguage.indexOf(it)
//                                                if (shoseFromLanguage || shoseToLanguage) {
                                                shoseFromLanguage = false
                                                shoseToLanguage = false
//
//                                                }
                                            },
                                        fontFamily = dosis_font[3],
                                        fontSize = 17.sp
                                    )
                                    Divider(
                                        thickness = (0.7).dp,
                                        modifier = Modifier
                                            .fillMaxWidth(0.96f)
                                            .padding(
                                                top = 10.dp,
                                                bottom = 10.dp
                                            ),
                                        color = Color(124, 124, 124, 104)
                                    )
                                }

                                item {
                                    Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                }
                            })


                    }


                }

            }

        }
        if (shoseToLanguage) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(1, 1, 1, 104))
                    .noRippleClickable {
                        if (shoseFromLanguage || shoseToLanguage) {
                            shoseFromLanguage = false
                            shoseToLanguage = false

                        }

                    }) {

                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .fillMaxHeight(0.45f)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.White)
                        .align(Alignment.Center)
                ) {

                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {


                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = stringResource(id = R.string.Chose_your_Language),
                                color = Color(22, 22, 22, 255),
                                modifier = Modifier
                                    .padding(10.dp),
                                fontFamily = dosis_font[0],
                                fontSize = 15.sp,
                            )


                        }

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(
                                    top = 10.dp,
                                    bottom = 10.dp
                                ),
                            color = Color(124, 124, 124, 188)
                        )

                        LazyColumn(
                            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally, content = {
                                items(listLanguage) {
                                    Text(
                                        text = it.lowercase().replaceFirstChar {
                                            if (it.isLowerCase()) it.titlecase() else it.toString()
                                        },
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .noRippleClickable {
                                                toLanguage = it
                                                toLanguageIndex = listLanguage.indexOf(it)
//                                                if (shoseFromLanguage || shoseToLanguage) {
                                                shoseFromLanguage = false
                                                shoseToLanguage = false
//
//                                                }
                                            },
                                        fontFamily = dosis_font[3],
                                        fontSize = 17.sp
                                    )
                                    Divider(
                                        thickness = (0.7).dp,
                                        modifier = Modifier
                                            .fillMaxWidth(0.96f)
                                            .padding(
                                                top = 10.dp,
                                                bottom = 10.dp
                                            ),
                                        color = Color(124, 124, 124, 104)
                                    )
                                }

                                item {
                                    Spacer(modifier = Modifier.padding(bottom = 10.dp))

                                }
                            })


                    }


                }

            }
        }

    }

//
}

