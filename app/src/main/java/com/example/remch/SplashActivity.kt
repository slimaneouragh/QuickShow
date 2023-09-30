package com.example.remch

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.remch.ui.theme.RemchTheme
import com.example.remch.ui.theme.dosis_font
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            CompositionLocalProvider(
                LocalDensity provides Density(
                    LocalDensity.current.density,
                    1f
                )
            ) {
                RemchTheme {
                    SplashScreen()
                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun SplashScreen() {


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
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }

//
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(220, 220, 220, 255)), contentAlignment = Alignment.Center
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
}