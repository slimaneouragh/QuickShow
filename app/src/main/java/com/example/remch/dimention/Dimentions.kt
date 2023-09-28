package com.example.remch.dimention

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp

class Dimentions {

    companion object {


        @Composable
        fun width(value: Float): Float {

            val config = LocalConfiguration.current
            return (value * config.screenWidthDp) / 100
        }

        @Composable
        fun height(value: Float): Float {
            val config = LocalConfiguration.current

            return (value * config.screenHeightDp) / 100

        }

        @Composable
        fun heightOfScreen(value: Float): Float {
            val config = LocalConfiguration.current

            return (config.screenHeightDp).toFloat() / value

        }
        @Composable
        fun heightOfScreenDP(): Float {
            val config = LocalConfiguration.current

            return config.screenHeightDp.toFloat()

        }

        @Composable
        fun widthOfScreenDP(): Float {
            val config = LocalConfiguration.current

            return config.screenWidthDp.toFloat()

        }

        @Composable
        fun widthOfScreen(value: Float): Float {

            val config = LocalConfiguration.current
            return (config.screenWidthDp).toFloat() / value
        }


    }

}