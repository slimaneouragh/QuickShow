package com.example.remch.utils.customComponante

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Translations
import com.example.remch.MyViewModel
import com.example.remch.R
import com.example.remch.ui.theme.dosis_font
import com.exyte.animatednavbar.utils.noRippleClickable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("StateFlowValueCalledInComposition")

@Composable
fun ListTranslationView(
    viewModel: MyViewModel, list: List<Translations?>,
    addSaveIcon: Boolean? = false,
    action: (translations: Translations?) -> Unit,
    second_action: ((translation: Translations?) -> Unit)? = null,
    optionalAction: ((any: Any?) -> Unit)? = null
) {


    val infiniteTransition = rememberInfiniteTransition()

    val scope = rememberCoroutineScope()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.07f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    var savedIDList = remember { mutableStateListOf<Int>() }
    var lazyListState = rememberLazyListState()

    val deletedList = remember { mutableStateListOf<Translations?>() }
    var selectedList = remember { mutableStateListOf<Int>() }
//    val selectedItem by remember { mutableStateOf(false) }


    if (selectedList.isNotEmpty()) {

        Row(
            modifier = Modifier
                .fillMaxWidth(0.89f)
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier
                    .noRippleClickable {
                        if (selectedList.isNotEmpty()) {
                            viewModel.viewModelScope.launch {
                                selectedList.forEach {
                                    deletedList.add(list[it])
                                    delay(300)
                                }

                                selectedList.forEach {
                                    withContext(Dispatchers.IO) {
                                        action.invoke(list[it])
                                    }
                                }
                                selectedList.clear()
                            }

                        }
                    },
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "delete", color = Color(255, 61, 0, 255),
                    fontFamily = dosis_font[0],
                    fontSize = 14.sp
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.delete_recycle_bin_trash_can_svgrepo_com),
                    contentDescription = "",
                    tint = Color(255, 61, 0, 255),
                    modifier = Modifier.size(18.dp)
                )
            }

            Row(
                modifier = Modifier
                    .noRippleClickable {
                        selectedList.clear()
                        selectedList.addAll(list.indices)

                    },
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "selectAll", color = Color(41, 121, 255, 255),
                    fontFamily = dosis_font[0],
                    fontSize = 14.sp
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.pin_svgrepo_com),
                    contentDescription = "",
                    tint = Color(41, 121, 255, 255),
                    modifier = Modifier.size(18.dp)
                )
            }

            if (addSaveIcon!!){
                Row(
                    modifier = Modifier
                        .noRippleClickable {
                            scope.launch {
                                selectedList.forEach { index ->
                                    withContext(Dispatchers.IO) {
                                        second_action?.invoke(list[index])
                                    }
                                }
                            }
                        },
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Save", color = Color(41, 121, 255, 255),
                        fontFamily = dosis_font[0],
                        fontSize = 14.sp
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.save_add_svgrepo_com),
                        contentDescription = "",
                        tint = Color(41, 121, 255, 255),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

        }

    }


    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxHeight(1f)
            .padding(top = 0.dp),
        content = {

            itemsIndexed(list, key = { index, item ->
                index
            }) { index, item ->

                AnimatedVisibility(
                    visible = !deletedList.contains(item),
                    exit = shrinkVertically(animationSpec = tween(1000)),
                    enter = expandVertically()
                ) {

                    Box(
                        modifier = Modifier
                            .then(Modifier.scale(if (selectedList.contains(index)) scale else 1f))
                            .padding(top = 20.dp)
                            .fillMaxWidth(0.85f)
                            .shadow(5.dp, RoundedCornerShape(15.dp), clip = true)
                            .clip(RoundedCornerShape(15.dp))
                            .then(
                                Modifier.background(
                                    if (selectedList.contains(index)) Color(
                                        255,
                                        61,
                                        0,
                                        255
                                    ) else LightGray
                                )
                            )
                            .combinedClickable(
                                onLongClick = {
                                    if (!selectedList.contains(index)) {
                                        selectedList.add(index)
                                        optionalAction?.invoke(null)
                                    }
                                }, onClick = {
                                    selectedList.remove(index)
                                })
//                            .clickable {
////                                viewModel.viewModelScope.launch {
//////                                            deletedList.add(item)
//////                                            delay(1000)
////                                    swipeToDismiss.invoke(item)
////                                }
//
//                            }
                            .border(
                                1.dp,
                                Color(220, 220, 220, 255),
                                RoundedCornerShape(15.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.align(Alignment.Center)
                        ) {

                            item?.let {
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = it.from,
                                        color = Color(22, 22, 22, 255),
                                        modifier = Modifier
                                            .padding(10.dp),
                                        fontFamily = dosis_font[0],
                                        fontSize = 12.sp
                                    )

                                    Icon(
                                        ImageVector.vectorResource(id = R.drawable.exchange_svgrepo_com),
                                        contentDescription = ""
                                    )

                                    Text(
                                        text = it.to,
                                        color = Color(22, 22, 22, 255),
                                        modifier = Modifier

                                            .padding(10.dp),

                                        fontFamily = dosis_font[0],
                                        fontSize = 12.sp
                                    )


                                }

                                Divider(
                                    modifier = Modifier.fillMaxWidth(0.9f),
                                    color = Color(124, 124, 124, 188)
                                )

                                Column(
                                    verticalArrangement = Arrangement.SpaceAround,
                                    horizontalAlignment = Alignment.Start,
                                ) {
                                    Text(
                                        text = it.textFrom,
                                        color = Color(22, 22, 22, 255),
                                        modifier = Modifier

                                            .padding(10.dp),
                                        fontFamily = dosis_font[0],
                                        fontSize = 15.sp
                                    )

                                    Text(
                                        text = it.textTo,
                                        color = Color(22, 22, 22, 255),
                                        modifier = Modifier

                                            .padding(10.dp),
                                        fontFamily = dosis_font[0],
                                        fontSize = 15.sp
                                    )

                                }

                            }
                        }

                        if (addSaveIcon == true) {
                            item?.let { item ->
                                if (!savedIDList.contains(item.id)) {
                                    if (!item.saved) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.save_add_svgrepo_com),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .padding(5.dp)
                                                .size(30.dp)
                                                .noRippleClickable {

                                                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                                                        second_action?.invoke(list[index])
                                                    }
                                                    savedIDList.add(item.id)
                                                }
                                        )
                                    }
                                }
                            }


                        }


                    }
                }

            }

//                }
//            }


        })
}