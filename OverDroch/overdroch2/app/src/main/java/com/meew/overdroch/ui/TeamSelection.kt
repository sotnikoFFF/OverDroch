package com.meew.overdroch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.meew.overdroch.R
import com.meew.overdroch.heroPickerService
import com.meew.overdroch.ui.data.HeroRow
import com.meew.overdroch.ui.screens.highlight

@Composable
fun TeamSelectionScreen(navController: NavController){
    Box {
        highlight()
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(R.drawable.background),
            contentDescription = "background_image",
            contentScale = ContentScale.FillWidth
        )
        Column(Modifier.background(color = Color.Transparent)) {
            HeroRow(heroPickerService!!.rowOfEnemies, { navController.navigate("teamSelection/enemies") }, Color.Red)
            Spacer(Modifier.height(20.dp))
            HeroRow(
                heroPickerService!!.rowOfOpponents,
                { navController.navigate("teamSelection/opponents") },
                Color.Blue
            )
        }
    }
}