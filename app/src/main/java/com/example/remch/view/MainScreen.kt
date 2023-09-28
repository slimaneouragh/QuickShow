package com.example.remch.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.strictmode.NonSdkApiUsedViolation
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.remch.MyViewModel
import com.example.remch.ui.theme.RemchTheme
import com.example.remch.utils.Navigation
import com.example.remch.utils.NavigationBarItems
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.BallAnimation
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(viewModel: MyViewModel) {
    val navController = rememberAnimatedNavController()
    val navigationBarItem = remember { NavigationBarItems.values() }
    var selectedIndex by remember { mutableStateOf(2) }
    var selectedIndexRemember by remember { mutableStateOf(2) }
    val vibrator = LocalContext.current.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    Scaffold(
        containerColor =  if (isSystemInDarkTheme()) {
            Color(27, 27, 27, 255)
        } else {
            Color(220, 220, 220, 255)
        },
        bottomBar = {
            AnimatedNavigationBar(
                selectedIndex = selectedIndex,
                cornerRadius = shapeCornerRadius(
                    topRight = 60.dp,
                    topLeft = 60.dp,
                    bottomLeft = 0.dp,
                    bottomRight = 0.dp
                ),
                ballAnimation = Parabolic(tween(300)),
                indentAnimation = Height(tween(200)),
                barColor =  if (isSystemInDarkTheme()) {
                    Color(148, 148, 148, 255)
                } else {
                    Color(170, 170, 170, 255)
                },
                ballColor =  if (isSystemInDarkTheme()) {
                    Color(185, 184, 184, 255)
                } else {
                    Color.Black
                }
            ) {
                navigationBarItem.forEach { item ->
                    Box(
                        modifier = Modifier
                            .noRippleClickable {
                                // this type of vibration requires API 29
                                val vibrationEffect2: VibrationEffect
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                                    // create vibrator effect with the constant EFFECT_CLICK
                                    vibrationEffect2 =
                                        VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)

                                    // it is safe to cancel other
                                    // vibrations currently taking place
                                    vibrator.cancel()
                                    vibrator.vibrate(vibrationEffect2)
                                }
                                navController.navigate(item.route){
                                    popUpTo(navController.graph.id){
                                        inclusive = false
                                    }
                                }

                                selectedIndex = item.ordinal
                            }
                            .fillMaxWidth()
                            .height(60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = item.icon),
                            contentDescription = "",
                            modifier = Modifier.size(26.dp),
                            tint = if (selectedIndex == item.ordinal) Color(
                                255,
                                193,
                                7,
                                255
                            ) else {
                                if (isSystemInDarkTheme()) {
                                    Color(27, 27, 27, 255)
                                } else {
                                    Color.Black
                                }
                            }
                        )

                    }

                }


            }
        }) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.925f)
            ) {
                Navigation(LocalContext.current, navController = navController,viewModel = viewModel)

        }



    }


}

