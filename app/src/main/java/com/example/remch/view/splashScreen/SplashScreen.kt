package com.example.remch.view.splashScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.remch.MainActivity
import com.example.remch.ui.theme.dosis_font
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(
    launchIntent: () -> Unit
) {

    val isSystemInDark = isSystemInDarkTheme()

    var size by remember {
        mutableStateOf(0f)
    }

    val scope = rememberCoroutineScope()

    val animateScale by animateFloatAsState(
        targetValue = size,
        label = "",
        animationSpec = tween(300)
    )


    scope.launch {
        size = 0.5f
        delay(300)
        launchIntent.invoke()
    }

//
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isSystemInDark) Color(32, 32, 32, 255) else Color(220, 220, 220, 255)
            ), contentAlignment = Alignment.Center
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .size(150.dp)


        ) {
            Text(
                text = "Quick",
                fontFamily = dosis_font[5],
                fontSize = 30.sp,
                fontWeight = FontWeight(2),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Divider(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
//                        .fillMaxWidth(0.5f)
                    .scale(
                        animateScale
                    ),
                thickness = 2.dp,
                color = Color(107, 107, 107, 255)
            )
            Text(
                text = "Show",
                fontFamily = dosis_font[5],
                fontSize = 30.sp,
                fontWeight = FontWeight(2),
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
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

    }

}