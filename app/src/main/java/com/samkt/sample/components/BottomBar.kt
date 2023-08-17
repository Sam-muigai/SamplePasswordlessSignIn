package com.samkt.sample.components

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.samkt.sample.R

data class BottomBarItem(
    @DrawableRes val icon:Int,
    val route:String,
    val isSelected:Boolean = false
)

val bottomNavItems =  listOf(
    BottomBarItem(R.drawable.home_solid_stroke,"notification",true),
    BottomBarItem(R.drawable.bell_stroke,"notification"),
    BottomBarItem(R.drawable.search_stroke,"notification"),
    BottomBarItem(R.drawable.mail_stroke,"notification"),

)