package com.example.remch.view.Camera_Screen

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.entity.Translations
import com.example.remch.MyViewModel
import com.example.remch.R
import com.example.remch.ui.theme.dosis_font
import com.example.remch.utils.startTextRecognition
import com.exyte.animatednavbar.utils.noRippleClickable
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun CameraScreen(
    viewModel: MyViewModel
) {
    CameraContent(
        viewModel = viewModel
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraContent(viewModel: MyViewModel) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context) }
    var detectedText by remember { mutableStateOf("No text detected yet") }

    var shoseFromLanguage by remember { mutableStateOf(false) }
    var shoseToLanguage by remember { mutableStateOf(false) }
    val listLanguage = viewModel.listLanguage.collectAsStateWithLifecycle().value


    var fromLanguage by remember { mutableStateOf(listLanguage[12]) }
    var toLanguage by remember { mutableStateOf(listLanguage[7]) }

    var toLanguageIndex by remember { mutableStateOf(7) }
    var fromLanguageIndex by remember { mutableStateOf(12) }
//    var medianLanguageIndex by remember { mutableStateOf(0) }


    var fromTextState by remember { mutableStateOf("") }
    var toTextState by remember { mutableStateOf("") }


    val listLanguageWithTAG = TranslateLanguage.getAllLanguages()
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

    if (detectedText.isNotEmpty()) {
        englishToarabicTranslation.translate(detectedText)
            .addOnSuccessListener {
                toTextState = it
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failure to translate this Word", Toast.LENGTH_LONG).show()
            }

    } else if (detectedText.isEmpty()) {
        toTextState = ""

    }


    fun onTextUpdate(updatedText: String) {
        detectedText = updatedText

    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = if (isSystemInDarkTheme()) {
            Color(27, 27, 27, 255)
        } else {
            Color(220, 220, 220, 255)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->


        Box(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxSize(0.97f)
                    .clip(RoundedCornerShape(20.dp))
                    .align(Alignment.Center),
                contentAlignment = Alignment.BottomCenter
            ) {

                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues), factory = { context ->

                        PreviewView(context).apply {
                            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                            setBackgroundColor(Color.Black.hashCode())
                            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                            scaleType = PreviewView.ScaleType.FILL_START
                        }.also { previewView ->
                            previewView.controller = cameraController
                            cameraController.bindToLifecycle(lifecycleOwner)
                            startTextRecognition(
                                context = context,
                                cameraController = cameraController,
                                lifecycleOwner = lifecycleOwner,
                                previewView = previewView,
                                onDetectedTextUpdated = ::onTextUpdate,
                                viewModel = viewModel
                            )
                        }
                    })

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(androidx.compose.ui.graphics.Color(211, 206, 206, 191))
                ) {
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    Text(
                        text =
                        fromLanguage
                            .lowercase()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .noRippleClickable {
                                shoseToLanguage = false
                                shoseFromLanguage = true
                            },
                    )

                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    Text(
                        text =
                        detectedText
                            .lowercase()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.padding(vertical = 10.dp))


                    Divider(
                        modifier = Modifier.fillMaxWidth(0.85f), color = Color(27, 27, 27, 255)
                    )

                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    Text(
                        text =
                        toLanguage
                            .lowercase()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .noRippleClickable {
                                shoseFromLanguage = false
                                shoseToLanguage = true
                            },
                    )

                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    Text(
                        text =
                        toTextState
                            .lowercase()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

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






            Card(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(100.dp)
                    .padding(all = 25.dp)
                    .noRippleClickable {


                        scope.launch {

                            val result = snackbarHostState.showSnackbar(
                                message = "The Translation is Saved",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Short
                            )

                            when (result) {
                                SnackbarResult.ActionPerformed -> {

                                }
                                SnackbarResult.Dismissed -> {
                                    viewModel.addTranslation(
                                        Translations(
                                            from = fromLanguage,
                                            to = toLanguage,
                                            textFrom = detectedText,
                                            textTo = toTextState
                                        )
                                    )
                                }
                            }

                        }

                    },
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.save_add_svgrepo_com),
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)

                )
            }


        }
    }
}