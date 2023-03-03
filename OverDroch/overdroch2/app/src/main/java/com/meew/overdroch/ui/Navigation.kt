package com.meew.overdroch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.meew.overdroch.R
import com.meew.overdroch.Wiki
import com.meew.overdroch.data.OverFastService
import com.meew.overdroch.data.hero.Hero
import com.meew.overdroch.heroPickerService
import com.meew.overdroch.ui.screens.*
val items = listOf("Heroes", "Wiki", "Maps", "Hero picker")
@Composable
fun BottomNavigationBar(onClick: (String) -> Unit) {
    var selectedItem = 0
    BottomNavigation(modifier = Modifier.clip(RoundedCornerShape(15.dp,15.dp,0.dp,0.dp))) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(item) },
                modifier = Modifier.background(Color.Black.copy(alpha = 0.8f)),
                selected = selectedItem == index,
                onClick = { onClick(item) })
        }
    }
}
@Composable
fun NavigationHost(bottomNav:(String) -> Unit,toMapNav:(String)->Unit, navController:NavHostController) {


    NavHost(navController, startDestination = "Heroes") {
        composable("Heroes") {
            Box {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.background),
                    contentDescription = "background_image",
                    contentScale = ContentScale.FillBounds
                )
                OverwatchHeroSelection(navController,
                    allHeroes = OverFastService.heroes as MutableList<Hero>,
                    onHeroSelected = { hero ->
                        navController.navigate("heroDetail/${hero.name}") {
                            launchSingleTop = true
                        }
                    },
                    bottomNav = { t -> bottomNav(t) },
                    topBar = {})
            }
        }
        composable(
            "heroDetail/{heroName}",
            arguments = listOf(navArgument("heroName") { type = NavType.StringType })
        ) { backStackEntry ->
            val heroId = backStackEntry.arguments!!.getString("heroName")
            heroId?.let { heroId ->
                var hero = OverFastService.heroes?.first { it.name == heroId }
                if (hero != null) {
                    HeroDetailScreen(hero = hero, bottomNav = { t-> bottomNav(t)})
                }
            }
        }
        composable(items[1]) {
            Wiki(navController) { t -> bottomNav(t)
            }
        }
        composable(items[2]) {
            Maps({ t -> bottomNav(t) }, { t->toMapNav(t) } , OverFastService.maps)
        }
        composable(items[3]) {
            TeamSelectionScreen(navController)
//            HeroPickerScreen(navController,{ t -> bottomNav(t) }, Color.Red, heroPickerService!!.rowOfEnemies)
        }
        composable("teamSelection/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })){ backStackEntry ->
            if(backStackEntry.arguments!!.getString("name") == "enemies")
                HeroPickerScreen(navController,{ t -> bottomNav(t) }, Color.Red, heroPickerService!!.rowOfEnemies)
            else
                HeroPickerScreen(navController,{ t -> bottomNav(t) }, Color.Blue, heroPickerService!!.rowOfOpponents)
        }


    }
}