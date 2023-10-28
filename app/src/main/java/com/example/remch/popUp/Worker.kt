package com.example.remch.popUp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Database
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.local.TranslationDatabase
import com.example.domain.entity.Translations
import com.example.remch.Window
import com.example.remch.utils.TypesOfFetchData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext


class Worker(context: Context, workparams: WorkerParameters) : CoroutineWorker(context, workparams) {

    val contex = context

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation", "WrongConstant")
    override suspend fun doWork(): Result {

        val taskdata = inputData
        val taskdataString = taskdata.getString("Text")

        var data: Flow<List<Translations>>? = null

        when(taskdataString){
            TypesOfFetchData.RANDOMLY.name ->{

                data = TranslationDatabase.getDatabase(contex).translationDao().getRandomTranslation()

            }
            TypesOfFetchData.LAST_THREE.name ->{

                data = TranslationDatabase.getDatabase(contex).translationDao().getThreeLastTranslation()

            }
            TypesOfFetchData.SAVED.name ->{

                data = TranslationDatabase.getDatabase(contex).translationDao().getAllSavedTranslation()

            }
        }




        val window = contex.getSystemService(ComponentActivity.WINDOW_SERVICE) as WindowManager

        val layoutParams = WindowManager.LayoutParams().apply {
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            format = PixelFormat.TRANSLUCENT
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            gravity = Gravity.CENTER
            width = 800 // Set the desired width
            height = 900 // Set the desired height
        }

        data?.collectLatest {
            withContext(Dispatchers.Main) {
                Window(window, layoutParams).open(
                        CustomOverlayView(
                        contex,
                        window,
                        layoutParams,
                        it
                    ).createView(
                        contex
                    )
                )
            }
        }


        return Result.success()
    }


}