package com.example.remch.utils

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.remch.R

enum class NavigationBarItems(val icon:Int , val route:String)  {



favorite(icon = R.drawable.archive_add_svgrepo_com,"favorite"),
camera(icon = R.drawable.camera_svgrepo_com,"camera"),
exchange(icon = R.drawable.exchange_svgrepo_com,"exchange"),
history(icon = R.drawable.history_svgrepo_com,"history"),
settings(icon = R.drawable.settings_adjust_svgrepo_com,"settings"),
//profile(icon = R.drawable.profile_circle_svgrepo_com,"profile"),

}