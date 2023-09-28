package com.example.remch

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.remch.popUp.Worker
import com.example.remch.ui.theme.RemchTheme
import com.example.remch.view.MainScreen
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        private const val SYSTEM_ALERT_WINDOW_PERMISSION_CODE = 1
    }

    val viewModel: MyViewModel by viewModels()

    @SuppressLint("StateFlowValueCalledInComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        lazy {
            viewModel.getFetchType()
            viewModel.getTime()
            viewModel.getState()
            viewModel.getAllTranslation()
            viewModel.getAllSavedTranslation()
        }

        if (!hasSystemAlertWindowPermission()) {
            requestSystemAlertWindowPermission()
        } else {

            setContent {


                CompositionLocalProvider(
                    LocalDensity provides Density(
                        LocalDensity.current.density,
                        1f
                    )
                ) {
                    RemchTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
//                                color = MaterialTheme.colorScheme.background,
//                                contentColor = MaterialTheme.colorScheme.background
                        ) {
                            MainScreen(viewModel)
                        }
                    }
                }
            }

        }
    }


    fun hasSystemAlertWindowPermission(): Boolean {
        return Settings.canDrawOverlays(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun requestSystemAlertWindowPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SYSTEM_ALERT_WINDOW
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SYSTEM_ALERT_WINDOW),
                SYSTEM_ALERT_WINDOW_PERMISSION_CODE
            )
        } else {
            setContent {
                CompositionLocalProvider(
                    LocalDensity provides Density(
                        LocalDensity.current.density,
                        1f
                    )
                ) {
                    RemchTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            MainScreen(viewModel)
                        }
                    }
                }
            }
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
            data = Uri.parse("package:$packageName")
        }
        startActivity(intent)
    }

    @Deprecated("Deprecated in Java")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SYSTEM_ALERT_WINDOW_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setContent {
                    CompositionLocalProvider(
                        LocalDensity provides Density(
                            LocalDensity.current.density,
                            1f
                        )
                    ) {
                        RemchTheme {
                            // A surface container using the 'background' color from the theme
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                MainScreen(viewModel)
                            }
                        }
                    }
                }
            } else {
                // Permission denied, handle accordingly
                openAppSettings()

            }
        }
    }


}

