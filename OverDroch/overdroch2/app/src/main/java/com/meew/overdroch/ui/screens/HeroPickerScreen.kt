package com.meew.overdroch.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.meew.overdroch.R
import com.meew.overdroch.data.OverFastDAO
import com.meew.overdroch.data.OverFastService
import com.meew.overdroch.data.hero.Hero
import com.meew.overdroch.heroPickerService
import com.meew.overdroch.ui.data.HeroRow

@Composable
fun HeroPickerScreen(navController:NavController, bottomNav:(String)->Unit, color: Color, row:MutableList<Hero?>) {
    var localContext = LocalContext.current
    var selectedHeroIndex by remember { mutableStateOf(0) };
    Box {
        highlight()
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.background),
                contentDescription = "background_image",
                contentScale = ContentScale.FillWidth
            )
        Column(Modifier.background(Color.Transparent)) {
            HeroRow(row,
                { selectedHeroIndex = it }, color)

            OverwatchHeroSelection(
                navController = navController,
                onHeroSelected = {

                    if (row.contains(it))
                        Toast.makeText(localContext, "Hero already exist", Toast.LENGTH_SHORT).show()
                    else
                        row[selectedHeroIndex] = it
                },
                OverFastService.heroes!!,
                { bottomNav(it) },
                {

                })

        }
    }
}

fun highlight(){
    heroPickerService!!.highlightHeroesByEnemy()
}