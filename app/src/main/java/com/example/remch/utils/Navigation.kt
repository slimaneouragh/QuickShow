package com.example.remch.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.remch.MyViewModel
import com.example.remch.view.Camera_Screen.Camera
import com.example.remch.view.Saved_Screen.SavedScreen
import com.example.remch.view.exchange_Screen.Exchange
import com.example.remch.view.history_Screen.History
import com.example.remch.view.settings_Screen.Settings
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    context: Context,
    navController: NavHostController,
    viewModel: MyViewModel,

    ) {


    AnimatedNavHost(navController, startDestination = NavigationBarItems.exchange.route) {

// ------------------------------------------ < Home > ---------------------------------------------


        composable(NavigationBarItems.exchange.route, exitTransition = {

            slideOutHorizontally(
                animationSpec = tween(50, easing = FastOutSlowInEasing),
                targetOffsetX = { -100 }) + fadeOut(animationSpec = tween(100))

        }, popEnterTransition = {


            slideInHorizontally(
                animationSpec = tween(
                    50,
                    easing = FastOutSlowInEasing
                )
            ) { -100 } + fadeIn(animationSpec = tween(100))
        }

        ) {
            Exchange(viewModel)

        }

        // ------------------------------------------ < Search > ---------------------------------------------

        composable(NavigationBarItems.settings.route,
            enterTransition = {

                slideInHorizontally(
                    animationSpec = tween(
                        100,
                        easing = FastOutSlowInEasing
                    )
                ) { 100 } + fadeIn(animationSpec = tween(100))

            }, popExitTransition = {


                slideOutHorizontally(
                    animationSpec = tween(
                        200,
                        easing = FastOutSlowInEasing
                    )
                ) { 100 } + fadeOut(animationSpec = tween(100))
            }

        ) {

            Settings(viewModel = viewModel, navController = navController)

        }

        // ------------------------------------------ < Account > ---------------------------------------------

        composable(NavigationBarItems.camera.route,
            enterTransition = {

                slideInHorizontally(
                    animationSpec = tween(
                        200,
                        easing = FastOutSlowInEasing
                    )
                ) { 400 } + fadeIn(animationSpec = tween(400))

            }, popExitTransition = {


                slideOutHorizontally(
                    animationSpec = tween(
                        200,
                        easing = FastOutSlowInEasing
                    )
                ) { 400 } + fadeOut(animationSpec = tween(400))
            }

        ) {
            Camera(viewModel = viewModel)

        }


        // ------------------------------------------ < About > ---------------------------------------------

        composable(NavigationBarItems.history.route,
            enterTransition = {

                slideInHorizontally(
                    animationSpec = tween(
                        100,
                        easing = FastOutSlowInEasing
                    )
                ) { 100 } + fadeIn(animationSpec = tween(100))

            }, popExitTransition = {


                slideOutHorizontally(
                    animationSpec = tween(
                        200,
                        easing = FastOutSlowInEasing
                    )
                ) { 100 } + fadeOut(animationSpec = tween(100))
            }

        ) {
            History(viewModel = viewModel)
        }


        composable(NavigationBarItems.favorite.route,
            enterTransition = {

                slideInHorizontally(
                    animationSpec = tween(
                        200,
                        easing = FastOutSlowInEasing
                    )
                ) { 400 } + fadeIn(animationSpec = tween(400))

            }, popExitTransition = {


                slideOutHorizontally(
                    animationSpec = tween(
                        200,
                        easing = FastOutSlowInEasing
                    )
                ) { 400 } + fadeOut(animationSpec = tween(400))
            }

        ) {

            SavedScreen(viewModel = viewModel)

        }


    }
}