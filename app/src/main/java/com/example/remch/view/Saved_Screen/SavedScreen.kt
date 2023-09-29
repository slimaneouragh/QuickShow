package com.example.remch.view.Saved_Screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Translations
import com.example.remch.MyViewModel
import com.example.remch.R
import com.example.remch.ui.theme.dosis_font
import com.example.remch.ui.theme.tsukimi_font
import com.example.remch.utils.customComponante.ListTranslationView
import com.example.remch.utils.SpinningProgressBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@Composable
fun SavedScreen(viewModel: MyViewModel) {


    val focusManager = LocalFocusManager.current
    viewModel.getAllSavedTranslation()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var loading by remember { mutableStateOf(true) }


    var list = viewModel.getAllSaved.value.collectAsStateWithLifecycle(initialValue = emptyList())
    val listfrom = remember {
        mutableStateListOf<Translations?>().apply {


            viewModel.viewModelScope.launch {

                viewModel.list_StateSaved.collect {
                    if (it != this@apply) {
                        this@apply.addAll(it)
                    }

                }
            }

        }
    }
    scope.launch {

        viewModel.first_launchSaved.collect {
            if (it) {
                loading = false
                viewModel.list_StateSaved.emit(list.value)
            } else {
                delay(1000)
                loading = false
                viewModel.list_StateSaved.emit(list.value)

                viewModel.viewModelScope.launch {
                    viewModel.first_launchSaved.emit(true)
                }
            }

        }

//        listfrom.addAll(list.value)


    }


    var searchState by remember { mutableStateOf("") }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = if (isSystemInDarkTheme()) {
            Color(27, 27, 27, 255)
        } else {
            Color(220, 220, 220, 255)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {


        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(220, 220, 220, 255)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = stringResource(id = R.string.Saved),
                    color = Color(22, 22, 22, 255),
                    modifier = Modifier.padding(top = 20.dp),
                    fontFamily = tsukimi_font,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))



                OutlinedTextField(
                    value = searchState, onValueChange = { searchState = it },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(0.9f)
                        .shadow(10.dp, RoundedCornerShape(20.dp), clip = true)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(220, 220, 220, 255)),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "") },
                    trailingIcon = {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.settings_adjust_svgrepo_com),
                            contentDescription = ""
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedLabelColor = Color.Transparent,
                        focusedLabelColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color(2, 22, 22, 255),
                        cursorColor = Color(2, 22, 22, 255),
                    ),
                    textStyle = TextStyle.Default.copy(
                        fontSize = 17.sp,
                        fontFamily = dosis_font[5]
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {

                            focusManager.clearFocus()
                        }
                    )
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(top = 30.dp, bottom = 5.dp),
                )

                if (loading) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        SpinningProgressBar(modifier = Modifier.align(Alignment.Center))

                    }
                }


                ListTranslationView(viewModel = viewModel, listfrom, action = {
                    scope.launch {

                        val result = snackbarHostState.showSnackbar(
                            message = "The Translation is UnSaved",
                            actionLabel = "Undo",
                            duration = SnackbarDuration.Short
                        )


                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                            }

                            SnackbarResult.Dismissed -> {
                                viewModel.updateTranslation(
                                    Translations(
                                        id = it!!.id,
                                        from = it.from,
                                        to = it.to,
                                        textFrom = it.textFrom,
                                        textTo = it.textTo,
                                        saved = false
                                    )
                                )

                            }
                        }

                    }
                })
                {


                }


            }

        }
    }
}