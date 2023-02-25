package com.meew.overdroch.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.meew.overdroch.data.OverFastDAO
import com.meew.overdroch.data.OverFastService
import com.meew.overdroch.data.hero.Hero
import com.meew.overdroch.data.maps.GameMap
import com.meew.overdroch.ui.theme.BlueWhite
import com.meew.overdroch.ui.theme.Grey


class MapInfo: ComponentActivity() {
    var service:OverFastDAO = OverFastService.service
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val value = intent.getStringExtra("mapName") //if it's a string you stored.

        setContent(){
            var navController= rememberNavController();
            var map :MutableState<GameMap> = remember { mutableStateOf(service.maps.filter({ m -> m.name.equals(value)}).first())  }
            var mapHeroes : MutableList<Hero> = remember { service.heroes!!.filter({ h -> h.location.contains(map.value.name)}).toMutableList()  }
            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    elevation = 10.dp
                ) {
                    AsyncImage(
                        model = map.value.screenshot as String,
                        contentDescription = map.value.name,
                        modifier = Modifier.background(BlueWhite.copy(alpha = 0.8f)).border(
                            width = 4.dp,
                            color = Grey,
                            shape = RoundedCornerShape(0.dp)
                        ),
                        alpha = 0.9f,
                        alignment = Alignment.TopCenter,
                    )
                }
                Text("Map name:\n${map.value.name}",Modifier.fillMaxWidth(), fontFamily = FontFamily.SansSerif, textAlign = TextAlign.Center, style =MaterialTheme.typography.subtitle1.copy(Color.White))
                Spacer(modifier = Modifier.height(10.dp))
                Text("Map location:\n${map.value.location!!}",Modifier.fillMaxWidth(), fontFamily = FontFamily.SansSerif, textAlign = TextAlign.Center, style = MaterialTheme.typography.subtitle1.copy(Color.White))
                Spacer(modifier = Modifier.height(10.dp))
                Text("Game modes:",Modifier.fillMaxWidth(), fontFamily = FontFamily.SansSerif, textAlign = TextAlign.Center, style =MaterialTheme.typography.subtitle1.copy(Color.White))
                for (mode in map.value.gamemodes!!)
                    Text(mode,Modifier.fillMaxWidth(), fontFamily = FontFamily.SansSerif, textAlign = TextAlign.Center, style = MaterialTheme.typography.body1.copy(Grey))
                Spacer(modifier = Modifier.height(10.dp))
                Text("Heroes from this map:",Modifier.fillMaxWidth(), fontFamily = FontFamily.SansSerif, textAlign = TextAlign.Center, style = MaterialTheme.typography.subtitle1.copy(Color.White))
                LazyVerticalGrid(  columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(15.dp).fillMaxWidth()) {
                itemsIndexed(mapHeroes){id, item ->
                    Spacer(modifier = Modifier.width(10.dp))
                        Column {
//                            Surface(onClick = {
//                                navController.navigate("heroDetail/${item.name}") {
//                                launchSingleTop = true
//                            }} ) {
                            Text(
                                item.name,
                                Modifier.fillMaxWidth(),
                                fontFamily = FontFamily.SansSerif,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.subtitle1.copy(Color.White)
                            )
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                elevation = 10.dp
                            ) {

                                AsyncImage(
                                    model = item.portrait as String,
                                    contentDescription = item.name,
                                    modifier = Modifier.background(BlueWhite.copy(alpha = 0.8f)).border(
                                        width = 4.dp,
                                        color = Grey,
                                        shape = RoundedCornerShape(0.dp)
                                    ),
                                    alpha = 0.9f,
                                    alignment = Alignment.TopCenter,
                                )
                            }
                            }
                        }
                    }
//                }
            }
        }
    }
}