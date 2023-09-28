package com.example.remch.utils

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remch.R
import com.example.remch.ui.theme.dosis_font
import com.exyte.animatednavbar.utils.noRippleClickable

@Composable
fun NoPermissionScreen(
    onRequestPermission: () -> Unit
) {

    NoPermissionContent(
        onRequestPermission = onRequestPermission
    )
}

@Composable
fun NoPermissionContent(
    onRequestPermission: () -> Unit
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(1, 1, 1, 104))
            .noRippleClickable {


            }) {

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .fillMaxHeight(0.35f)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .align(Alignment.Center)
        ) {

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {


                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(id = R.string.CameraPermission),
                        color = Color(22, 22, 22, 255),
                        modifier = Modifier
                            .padding(10.dp),
                        fontFamily = dosis_font[0],
                        fontSize = 15.sp,
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(
//                                top = 10.dp,
                                bottom = 10.dp
                            ),
                        color = Color(124, 124, 124, 188)
                    )
                }




                Text(
                    text = "Pleas Get Permission to accesses to the camera",
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier.noRippleClickable {
                        onRequestPermission.invoke()

                    }
                ) {

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.camera_non_permission),
                        contentDescription = null,
                        tint = Color(0, 95, 255, 255)
                    )


                    Text(
                        text = "Grant Permission", modifier = Modifier.padding(start = 10.dp),
                        color = Color(2, 93, 245, 255)
                    )
                }


            }


        }

    }

}