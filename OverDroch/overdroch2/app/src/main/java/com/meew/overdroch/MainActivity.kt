package com.meew.overdroch

import android.graphics.Paint.Style
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.Coil
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.target.ImageViewTarget
import com.meew.overdroch.data.Hero
import com.meew.overdroch.data.OverFastService
import com.meew.overdroch.data.Role
import com.meew.overdroch.ui.theme.BlueWhite
import com.meew.overdroch.ui.theme.Grey
import com.meew.overdroch.ui.theme.Orange
import com.meew.overdroch.ui.theme.OverdrochTheme
import kotlinx.coroutines.currentCoroutineContext
import kotlin.coroutines.coroutineContext

val items = listOf("Heroes", "Wiki", "Maps")
class MainActivity : ComponentActivity() {

    val service: OverFastService = OverFastService()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var allHeroes: List<Hero> = service.getAllHeroes()
        setContent {
            OverdrochTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    SearchBar()
                    OverwatchHeroSelection(
                        onHeroSelected = { t -> Toast.makeText(applicationContext, t.name, Toast.LENGTH_SHORT).show() },
                        allHeroes as MutableList<Hero>
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Composable
fun DefaultPreview() {
    OverdrochTheme {
        Greeting("Android")
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OverwatchHeroSelection(onHeroSelected: (Hero) -> Unit, allHeroes: MutableList<Hero>) {
    var selectedHero by remember { mutableStateOf<Hero?>(null) }

    BackHandler {
        selectedHero = null
    }
    val column1Weight = .1f // 30%
    val column2Weight = .7f // 70%
    val damageHeroes: MutableList<Hero?>? = mutableListOf(null)
    val supportHeroes: MutableList<Hero?>? = mutableListOf(null)
    val tankHeroes: MutableList<Hero?>? = mutableListOf(null)
    allHeroes.filter({ hero: Hero -> hero.role == Role.damage }).toCollection(damageHeroes!!).toMutableList()
    allHeroes.filter({ hero: Hero -> hero.role == Role.support }).toCollection(supportHeroes!!).toMutableList()
    allHeroes.filter({ hero: Hero -> hero.role == Role.tank }).toCollection(tankHeroes!!).toMutableList()
    val lazyGridState = rememberLazyGridState()
    var cols: Int = 3
    var selectedItem = 0;
    var state: SearchState = rememberSearchState()


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black.copy(alpha = 0.8f),
                title = { Text(text = "Select a hero") }
            )
        },
        bottomBar = {
            BottomNavigation {
                items.forEachIndexed { index, item ->

                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text(item) },
                        modifier = Modifier.background(Color.Black.copy(alpha = 0.8f)),
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(cols),
            contentPadding = PaddingValues(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(1.dp)
        ) {
            item( span = {
                GridItemSpan(cols)
            }){
                OverwatchContentSpacer("Tank")
            }
            itemsIndexed(
                tankHeroes) { id,a ->

                if (a != null) {
                    HeroItem(hero = a,
                        isSelected = a == selectedHero,
                        onClick = {
                            selectedHero = a
                            onHeroSelected(a)
                        })
                }
            }
            item(span = {
                GridItemSpan(cols)
            }){
                OverwatchContentSpacer("Damage")
            }
            itemsIndexed(damageHeroes) { id,a ->

                if (a != null) {
                    HeroItem(hero = a,
                        isSelected = a == selectedHero,
                        onClick = {
                            selectedHero = a
                            onHeroSelected(a)
                        })
                }
            }
            item( span = {
                GridItemSpan(cols)
            }){
                OverwatchContentSpacer("Support")
            }
            itemsIndexed(supportHeroes) { id,a ->

                if (a != null) {
                    HeroItem(hero = a,
                        isSelected = a == selectedHero,
                        onClick = {
                            selectedHero = a
                            onHeroSelected(a)
                        })

                }
            }
            item( span = {
                GridItemSpan(cols)
            }){
                OverwatchContentSpacer("Support")
            }
        }

    }
}

@Composable
fun OverwatchContentSpacer(text: String) {
    Spacer(modifier = Modifier.height(40.dp))
    Surface (
        Modifier
            .fillMaxWidth()
            .height(60.dp),
            shape = RoundedCornerShape(30.dp),
            color = Orange.copy(alpha = 0.4f)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text, modifier = Modifier.fillMaxWidth(), fontFamily = FontFamily.SansSerif, textAlign = TextAlign.Center, style = MaterialTheme.typography.h4)
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}
@Composable
fun CategoryCard(
    category: String,
    imageRes: Int?,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .height(90.dp).width(30.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, BlueWhite.copy(alpha = 0.2f)),
        color = BlueWhite.copy(alpha = 0.1f)
    ) {
        Column(modifier = Modifier) {
//            Image(
//                painter = painterResource(id = imageRes),
//                contentDescription = category,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(100.dp)
//            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category,
                style = MaterialTheme.typography.subtitle1,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(start = 0.dp, end = 0.dp)
            )
        }
    }

}

@Composable
fun HeroItem(hero: Hero, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick).background(Grey.copy(alpha = 0.8f), shape = RoundedCornerShape(20.dp)).border(
                width = 2.dp,
                color = Grey,
                shape = RoundedCornerShape(23.dp)
            ).padding(10.dp).imePadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = hero.portrait as String,
                contentDescription = hero.name,
                modifier = Modifier.background(BlueWhite.copy(alpha = 0.8f)).border(
                    width = 4.dp,
                    color = Grey,
                    shape = RoundedCornerShape(0.dp)
                ),
                alpha = 0.9f,
                alignment = Alignment.TopCenter,
            )
            Text(
                text = hero.name,
                style = MaterialTheme.typography.caption,
                color = if (isSelected) Color.White else Color.Unspecified
            )
        }
    }
//        Text(
//            text = ,
//            style = MaterialTheme.typography.h6,
//            color = if (isSelected) Color.White else Color.Unspecified
//        )
    }


