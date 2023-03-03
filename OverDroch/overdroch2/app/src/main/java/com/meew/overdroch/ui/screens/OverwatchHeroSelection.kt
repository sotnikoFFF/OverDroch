package com.meew.overdroch.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.meew.overdroch.ExpandableSearchView
import com.meew.overdroch.R
import com.meew.overdroch.data.OverFastService
import com.meew.overdroch.data.hero.Hero
import com.meew.overdroch.data.hero.Role
import com.meew.overdroch.ui.BottomNavigationBar
import com.meew.overdroch.ui.data.HeroGrid
import com.meew.overdroch.ui.data.TabRowItem
import com.meew.overdroch.ui.theme.BlueWhite
import com.meew.overdroch.ui.theme.Grey
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OverwatchHeroSelection(
    navController: NavController,
    onHeroSelected: (Hero) -> Unit,
    allHeroes: MutableList<Hero>,
    bottomNav: (String) -> Unit,
    topBar:() -> Unit
) {
    var selectedHero by remember { mutableStateOf<Hero?>(null) }

    BackHandler {
        selectedHero = null
    }
    val damageHeroes: ArrayList<Hero?>? = ArrayList()
    val supportHeroes: ArrayList<Hero?>? = ArrayList()
    val tankHeroes: ArrayList<Hero?>? = ArrayList()
    for (hero in allHeroes)
        when (hero.role) {
            Role.damage -> damageHeroes!!.add(hero)
            Role.support -> supportHeroes!!.add(hero)
            Role.tank -> tankHeroes!!.add(hero)
            else -> {}
        }
    val tabRowItems = listOf(
         TabRowItem(
            title = Role.tank.toString(),
            screen = {
                HeroGrid(
                    tankHeroes
                ) { t -> onHeroSelected(t) }
            },
            icon = R.drawable.tank_icon,
        ),TabRowItem(
                title = Role.support.toString(),
        screen = {
            HeroGrid(
                supportHeroes
            ) { t -> onHeroSelected(t) }
        },
        icon = R.drawable.support_icon,
    ), TabRowItem(
            title = Role.damage.toString(),
            screen = {
                HeroGrid(
                    damageHeroes
                ) { t -> onHeroSelected(t) }
            },
            icon = R.drawable.damage_icon,
        ),
    )
    val pagerState = rememberPagerState(3)
    val coroutineScope = rememberCoroutineScope()
    var itemsClickable: Boolean = true;

        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {topBar()},
            bottomBar = { BottomNavigationBar { t -> bottomNav(t) } },
        )
        {
            HeroSearch(navController) { itemsClickable = !itemsClickable }
            Column(
                modifier = Modifier.padding(15.dp).zIndex(0f)
            ) {
                Spacer(Modifier.height(70.dp))
                TabRow(
                    selectedTabIndex = pagerState.currentPage, indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions).clickable(
                                enabled = itemsClickable,
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = { },
                            ), color = BlueWhite
                        )
                    }, modifier = Modifier
                ) {
                    tabRowItems.forEachIndexed { index, item ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = { coroutineScope.launch { pagerState.scrollToPage(index) } },
                            text = {
                                Row {
                                    Image(
                                        painterResource(item.icon),
                                        contentDescription = "",
                                        colorFilter = ColorFilter.tint(
                                            Black
                                        ),
                                        modifier = Modifier.padding(0.dp, 2.dp)
                                    )
                                    Spacer(Modifier.width(10.dp))
                                    Text(
                                        text = item.title,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        color = Black,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            },
                            modifier = Modifier.background(White)
                        )
                    }
                }
                HorizontalPager(
                    count = tabRowItems.size,
                    state = pagerState,
                    userScrollEnabled = true,
                    verticalAlignment = Alignment.CenterVertically
                    ) {
                    tabRowItems[it].screen()
                }
            }
        }
    }


//}


@Composable
fun HeroSearch(navController: NavController, clickable: () -> Unit) {
    var searchValue by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.padding(15.dp).zIndex(1f).background(
            White.copy(alpha = 0.8f)
        ).fillMaxWidth().clickable { clickable() }
    ) {
        ExpandableSearchView(
            "",
            { v -> searchValue = v },
            {},
            Modifier,
            false,
            Gray,
            searchItems = OverFastService.heroes!!.filter { h -> h.name.contains(searchValue) }
                .toMutableStateList(),
            onItemClick = { t -> navController.navigate("heroDetail/$t") })
    }
}

@Composable
fun HeroItem(hero: Hero, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier, shape: RoundedCornerShape) {
    Card(
        modifier =modifier.clickable(onClick = onClick), shape = shape
    ) {
        Column(verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = hero.portrait as String,
                contentDescription = hero.name,
                modifier = Modifier.border(
                    width = 1.dp, color = Grey.copy(alpha = 0.5f), shape = RoundedCornerShape(10.dp)
                ).clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)).padding(0.dp).background(if(isSelected)Color.Gray else White),
                alpha = 0.9f,
                alignment = Alignment.TopCenter,
            )
            Text(
                text = hero.name,
                style = MaterialTheme.typography.caption,
                color = Color.Unspecified
            )
        }
    }
}
@Composable
fun EmptyHeroItem(onClick: () -> Unit, isSelected: Boolean, modifier: Modifier, shape: RoundedCornerShape,) {

    Card(
        modifier = modifier.clickable(onClick = onClick), shape =shape
    ) {
        Column(verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
            Box(
                modifier = Modifier.width(90.dp).height(93.dp).border(
                    width = 1.dp, color = Grey.copy(alpha = 0.5f), shape = RoundedCornerShape(10.dp)
                ).clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)).padding(0.dp).background(if(isSelected)Color.Gray else White).fillMaxWidth()
            ){}
            Text(
                text = "EMPTY",
                style = MaterialTheme.typography.caption,
                color = Color.Unspecified
            )
        }
    }
}
