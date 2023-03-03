package com.meew.overdroch.ui.data

import androidx.compose.runtime.Composable

data class TabRowItem(
    val title: String,
    val icon: Int,
    val screen: @Composable () -> Unit,

)