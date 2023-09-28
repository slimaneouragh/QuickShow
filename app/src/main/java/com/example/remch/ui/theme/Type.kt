package com.example.remch.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

//val Typography = Typography(
//    bodyLarge = TextStyle(
//        fontFamily = apple_font,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp,
//        color = Color(0Xddffffff)
//    ),
//     //Other default text styles to override
//    titleLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 22.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp
//    ),
//    labelSmall = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Medium,
//        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp
//    )
//
//)


// set of dark material typography styles to start with.
val DarkTypography = Typography(
    displayMedium = TextStyle(
        fontFamily = tsukimi_font,
//        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontSize = 20.sp
    ),
    displaySmall = TextStyle(
        fontFamily = dosis_font.last(),
//        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontSize = 19.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = dosis_font[3],
//        fontWeight = FontWeight.Normal,
        color = Color.White,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = apple_font,
        fontWeight = FontWeight.Normal,
        color = Color.White,
        fontSize = 18.sp
    )
)
// set of light material typography styles to start with.
val LightTypography = Typography(
    displayMedium = TextStyle(
        fontFamily = tsukimi_font,
//        fontWeight = FontWeight.Bold,
        color = Color(22, 22, 22, 255),
        fontSize = 20.sp
    ),
    displaySmall = TextStyle(
        fontFamily = dosis_font.last(),
//        fontWeight = FontWeight.Bold,
        color = Color(22, 22, 22, 255),
        fontSize = 19.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = dosis_font[3],
//        fontWeight = FontWeight.Normal,
        color = Color(22, 22, 22, 255),
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = apple_font,
        fontWeight = FontWeight.Normal,
        color = Color(22, 22, 22, 255),
        fontSize = 18.sp
    )
)



