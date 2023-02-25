package com.meew.overdroch

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.meew.overdroch.ui.BottomNavigationBar

@Composable
fun Wiki(navController:NavController, onBottomNav:(String) -> Unit){
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black.copy(alpha = 0.8f),
                title = { Text(text = "Select a hero") }
            )
        },
        bottomBar = {
            BottomNavigationBar { t -> onBottomNav(t) }
        }
    ){

    }
}