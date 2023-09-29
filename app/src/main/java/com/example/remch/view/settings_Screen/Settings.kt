package com.example.remch.view.settings_Screen

import android.annotation.SuppressLint
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.chargemap.compose.numberpicker.AMPMHoursNumberPicker
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.domain.entity.Translations
import com.example.remch.MainActivity
import com.example.remch.MyViewModel
import com.example.remch.R
import com.example.remch.popUp.Worker
import com.example.remch.ui.theme.apple_font
import com.example.remch.ui.theme.dosis_font
import com.example.remch.ui.theme.tsukimi_font
import com.example.remch.utils.TypesOfFetchData
import com.exyte.animatednavbar.utils.noRippleClickable
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint(
    "StateFlowValueCalledInComposition", "RestrictedApi",
    "UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition"
)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Settings(viewModel: MyViewModel, navController: NavController) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val verticalScrolState = rememberScrollState()

    var checked by remember { mutableStateOf(viewModel.getState.value) }
    var enabled by remember { mutableStateOf(viewModel.getState.value) }

    val snackbarHostState = remember { SnackbarHostState() }


    val timerValue = viewModel.getTime.collectAsStateWithLifecycle()
    var triggerSnackbar = viewModel.triggerSnackBar.collectAsStateWithLifecycle()
    var fetchDataType = viewModel.fetchType.collectAsStateWithLifecycle()

    var pickerValueHours by remember {
        mutableStateOf(
            if (timerValue.value >= 60) {
                ((timerValue.value) / 60).toInt()
            } else {
                0
            }
        )
    }

    var pickerValueMinutes by remember {
        mutableStateOf(
            if (timerValue.value >= 60) {
                (timerValue.value) - (60 * ((timerValue.value) / 60))
            } else {
                timerValue.value
            }
        )
    }

    val contentAlpha = if (enabled) ContentAlpha.high else ContentAlpha.disabled

    var visibleTimePicker by remember { mutableStateOf(viewModel.getState.value) }
    var visibleTextAbout by remember { mutableStateOf(false) }

    var Allert by remember { mutableStateOf(false) }

    val worker: WorkManager = WorkManager.getInstance(context)


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = if (isSystemInDarkTheme()) {
            Color(27, 27, 27, 255)
        } else {
            Color(220, 220, 220, 255)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->

                Snackbar(modifier = Modifier.padding(4.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp)
                    ) {
                        Text(
                            text = "Make Any Translation before to Activate this Option",
                            color = White,
                            fontFamily = dosis_font[3]
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.triangle_warning_svgrepo_com),
                            contentDescription = "",
                            tint = Color(255, 196, 0, 255)
                        )
                    }

                }
            }
        }
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
                    text = stringResource(id = R.string.Settings),
                    color = Color(22, 22, 22, 255),
                    modifier = Modifier.padding(top = 20.dp),
                    fontFamily = tsukimi_font,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))


                Divider(
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .fillMaxWidth(0.8f)
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .toggleable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            value = checked,
                            enabled = true,
                            role = Role.Switch,
                            onValueChange = {
                                if (it) {
                                    viewModel.viewModelScope.launch {
                                        viewModel.changeState(state = true)
                                    }
                                } else if (!it) {
                                    viewModel.viewModelScope.launch {
                                        viewModel.changeState(state = false)
                                    }
                                    worker.cancelAllWork()
                                    Allert = false
                                }
                                checked = it
                                enabled = enabled.not()
                                visibleTimePicker = visibleTimePicker.not()
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1.0f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Text(
                            text = "Pop Up",
                            fontFamily = dosis_font[5],
                            maxLines = 1,
                            fontWeight = FontWeight(2),
                            modifier = Modifier.alpha(contentAlpha)
                        )
                        Text(
                            text = "Activate For Display Latest Translation Over Other Apps",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.alpha(contentAlpha)
                        )
                    }

                    Switch(
                        checked = checked,
                        onCheckedChange = null,
                        enabled = enabled,
                    )
                }





                AnimatedVisibility(visible = visibleTimePicker) {

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Divider(
                            modifier = Modifier
                                .padding(bottom = 5.dp)
                                .fillMaxWidth(0.6f)
                        )


                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.89f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f, false)
                            )
                            {

                                fetchDataType.value.let {

                                    Row(
                                        modifier = Modifier
                                            .noRippleClickable {
                                                viewModel.changeFetchDataType(TypesOfFetchData.RANDOMLY)
                                            },
                                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ){
                                        Text(
                                            text = "RANDOMLY", color =
                                            if (it == TypesOfFetchData.RANDOMLY.name)
                                                Color(
                                                    41,
                                                    121,
                                                    255,
                                                    255
                                                ) else Color(255, 145, 0, 255),
                                            fontFamily = dosis_font[0],
                                            fontSize = 14.sp
                                        )
                                        Icon(
                                            imageVector = ImageVector.vectorResource(id = R.drawable.random_svgrepo_com),
                                            contentDescription = "",
                                            tint =
                                            if (it == TypesOfFetchData.RANDOMLY.name)
                                                Color(
                                                    41,
                                                    121,
                                                    255,
                                                    255
                                                ) else Color(255, 145, 0, 255),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    if (it == TypesOfFetchData.RANDOMLY.name) {
                                        Divider(Modifier.fillMaxWidth(0.7f),
                                            color = Color(1,1,1))
                                    }


                                }
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f, false)
                            ) {

                                fetchDataType.value.let {


                                    Row(
                                        modifier = Modifier
                                            .noRippleClickable {
                                                viewModel.changeFetchDataType(TypesOfFetchData.LAST_THREE)
                                            },
                                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "LAST_THREE", color =
                                            if (it == TypesOfFetchData.LAST_THREE.name)
                                                Color(
                                                    41,
                                                    121,
                                                    255,
                                                    255
                                                ) else Color(255, 145, 0, 255),
                                            fontFamily = dosis_font[0],
                                            fontSize = 14.sp
                                        )
                                        Icon(
                                            imageVector = ImageVector.vectorResource(id = R.drawable.list_pointers_svgrepo_com),
                                            contentDescription = "",
                                            tint =
                                            if (it == TypesOfFetchData.LAST_THREE.name)
                                                Color(
                                                    41,
                                                    121,
                                                    255,
                                                    255
                                                ) else Color(255, 145, 0, 255),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    if (it == TypesOfFetchData.LAST_THREE.name) {
                                        Divider(Modifier.fillMaxWidth(0.7f),
                                            color = Color(1,1,1))
                                    }


                                }
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f, false)
                            ) {

                                fetchDataType.value.let {


                                    Row(
                                        modifier = Modifier
                                            .noRippleClickable {
                                                viewModel.changeFetchDataType(TypesOfFetchData.HYBRID)
                                            },
                                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "HYBRID", color =
                                            if (it == TypesOfFetchData.HYBRID.name)
                                                Color(
                                                    41,
                                                    121,
                                                    255,
                                                    255
                                                ) else Color(255, 145, 0, 255),
                                            fontFamily = dosis_font[0],
                                            fontSize = 14.sp
                                        )
                                        Icon(
                                            imageVector = ImageVector.vectorResource(id = R.drawable.hybrid_networking_alt_svgrepo_com),
                                            contentDescription = "",
                                            tint =
                                            if (it == TypesOfFetchData.HYBRID.name)
                                                Color(
                                                    41,
                                                    121,
                                                    255,
                                                    255
                                                ) else Color(255, 145, 0, 255),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    if (it == TypesOfFetchData.HYBRID.name) {
                                        Divider(Modifier.fillMaxWidth(0.7f),
                                            color = Color(1,1,1))
                                    }


                                }
                            }


                        }

                        Divider(
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .fillMaxWidth(0.6f)
                        )


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
//                        .alpha(contentAlpha)
                            ,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            NumberPicker(
                                value = pickerValueHours,
                                range = 0..24,
                                onValueChange = {
                                    pickerValueHours = it
                                },
                                dividersColor = Color(87, 87, 87, 164)

                            )
                            Text(text = "Hours")
                            NumberPicker(
                                value = pickerValueMinutes,
                                range = 0..60,
                                onValueChange = {
                                    pickerValueMinutes = it
                                },
                                dividersColor = Color(87, 87, 87, 164)
                            )
                            Text(text = "Minutes")

                            IconButton(onClick = {
                                if (((pickerValueHours * 60) + pickerValueMinutes).toLong() < 15) {

                                    Allert = true

                                } else {
                                    Allert = false

                                    viewModel.viewModelScope.launch {
                                        viewModel.changeTime(((pickerValueHours * 60) + pickerValueMinutes))
                                        viewModel.changeState(true)

                                        viewModel.launchWorker(
                                            pickerValueHours = pickerValueHours,
                                            pickerValueMinutes = pickerValueMinutes,
                                            type = TypesOfFetchData.LAST_THREE,
                                            context = context
                                        )
                                    }

                                }


                            }) {
                                Icon(
                                    ImageVector.vectorResource(id = R.drawable.submit_success_check_mark_svgrepo_com),
                                    contentDescription = "",
                                    tint = Color.Black
                                )
                            }

                        }

                    }


                }
//            Divider(
//                modifier = Modifier
//                    .padding(top = 5.dp, bottom = 5.dp)
//                    .fillMaxWidth(0.5f),
//                color = Color.Red
//            )
                AnimatedVisibility(visible = Allert) {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.exclamation_mark_circle_svgrepo_com),
                            contentDescription = "",
                            tint = Color.Red
                        )
                        Text(
                            text = "Minimal Time Of Repetition is 15 Minute",
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontFamily = apple_font,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
//                Divider(
//                    modifier = Modifier
//                        .padding(top = 5.dp, bottom = 5.dp)
//                        .fillMaxWidth(0.5f),
//                    color = Color.Red
//                )
                }

                Divider(
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .fillMaxWidth(0.9f)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .toggleable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            value = checked,
                            enabled = true,
                            role = Role.Switch,
                            onValueChange = {
                                visibleTextAbout = visibleTextAbout.not()
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier.weight(1.0f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Text(
                            text = "About",
                            fontFamily = dosis_font[5],
                            fontSize = 20.sp,
                            fontWeight = FontWeight(2)
//                        modifier = Modifier.alpha(contentAlpha)
                        )

                    }

                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.down_arrow_5_svgrepo_com
                        ),
                        contentDescription = "",
                        modifier = Modifier.rotate(if (visibleTextAbout) 180f else 0f)
                    )


                }

                AnimatedVisibility(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 5.dp),
                    visible = visibleTextAbout
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.verticalScroll(verticalScrolState)

                    ) {

                        Text(
                            text = "It is an application to help learn any language by repetition " +
                                    "by showing a pop-up screen every time period specified by the  user",
                            fontFamily = dosis_font[3],

                            )

                        Divider(
                            modifier = Modifier
                                .padding(top = 20.dp, bottom = 20.dp)
                                .fillMaxWidth(0.5f)
                        )

                        Text(
                            text = "Welcome . We are a software development and programming team for various operating platforms." +
                                    " We also have an industry footprint." +
                                    " We have established a startup specializing in the study and processing of vibrations." +
                                    " In short, we are a team who care about technology",
                            fontFamily = dosis_font[3],

                            )
                    }


                }

//                Divider(
//                    modifier = Modifier
//                        .padding(top = 20.dp, bottom = 20.dp)
//                        .fillMaxWidth(0.9f)
//                )

            }

            Text(
                text = "Cooperate@ With SAS Company \n 2021 / 2023",
                fontFamily = dosis_font[3],
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        bottom = 20.dp
                    )
            )

            if (triggerSnackbar.value) {

                scope.launch {

                    snackbarHostState.showSnackbar(
                        message = "",
                        duration = SnackbarDuration.Short
                    )

                    viewModel.updateTriggerSnackBar(false)

                }
            }


        }


    }


}