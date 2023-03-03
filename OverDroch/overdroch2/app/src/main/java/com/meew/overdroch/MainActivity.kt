package com.meew.overdroch

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.meew.overdroch.data.HeroPickerService

import com.meew.overdroch.data.hero.Hero
import com.meew.overdroch.data.OverFastService
import com.meew.overdroch.data.hero.Role
import com.meew.overdroch.ui.NavigationHost
import com.meew.overdroch.ui.data.HeroGrid
import com.meew.overdroch.ui.data.TabRowItem
import com.meew.overdroch.ui.items
import com.meew.overdroch.ui.screens.*

var heroPickerService: HeroPickerService? = null

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        heroPickerService = OverFastService().heroPickerService

        setContent {
            var navController = rememberNavController()
            NavigationHost({
                when(it){
                    items[3] ->  navController.navigate(items[3])
                    items[0] -> navController.navigate(items[0])
                    items[1] -> navController.navigate(items[1])
                    items[2] -> navController.navigate(items[2])
                }
            }, toMapNav ={ t -> startActivity(Intent(this@MainActivity, MapInfo::class.java).putExtra("mapName",t)) }, navController = navController)
        }
    }


}

//        Text(
//            text = ,
//            style = MaterialTheme.typography.h6,
//            color = if (isSelected) Color.White else Color.Unspecified
//        )



