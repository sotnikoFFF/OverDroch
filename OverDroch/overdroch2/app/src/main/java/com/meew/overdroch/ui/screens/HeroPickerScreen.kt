package com.meew.overdroch.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.meew.overdroch.R
import com.meew.overdroch.data.OverFastDAO
import com.meew.overdroch.data.OverFastService
import com.meew.overdroch.data.hero.Hero
import com.meew.overdroch.data.hero.Role
import com.meew.overdroch.ui.data.HeroGrid
import com.meew.overdroch.ui.data.TabRowItem
import com.meew.overdroch.ui.theme.Grey
import kotlinx.coroutines.launch

class HeroPickerScreen:ComponentActivity() {

    var service: OverFastDAO = OverFastService.service
    var pickedHeroes:MutableList<Hero> = mutableStateListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeroPickerScreen()
        }
    }
    val tabRowItems = listOf(
        TabRowItem(
            title = Role.damage.toString(),
            screen = { HeroGrid(service.heroes!!.filter { h -> h.role == Role.damage }.toMutableStateList()) { t ->
                if (pickedHeroes.contains(
                        t
                    )
                ) pickedHeroes.remove(t) else pickedHeroes.add(t)
            }
            },
            icon = R.drawable.tank_icon,
        ),
        TabRowItem(
            title = Role.support.toString(),
            screen = { HeroGrid(service.heroes!!.filter { h -> h.role == Role.support }.toMutableStateList()) { t ->
                if (pickedHeroes.contains(
                        t
                    )
                ) pickedHeroes.remove(t) else pickedHeroes.add(t)
            }
            },
            icon = R.drawable.tank_icon,
        ),
        TabRowItem(
            title = Role.tank.toString(),
            screen = {  HeroGrid(service.heroes!!.filter { h -> h.role == Role.tank }.toMutableStateList()) { t ->
                if (pickedHeroes.contains(
                        t
                    )
                ) pickedHeroes.remove(t) else pickedHeroes.add(t)
            }
            },
            icon = R.drawable.tank_icon,
        )
    )
    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun HeroPickerScreen(){
        var heroes : MutableList<Hero> = remember { service.heroes!!.toMutableList()  }
        val pagerState = rememberPagerState(3)
        val coroutineScope = rememberCoroutineScope()
        Scaffold(
            topBar = {HeroPickerTopBar()}
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                            color = MaterialTheme.colors.secondary
                        )
                    },
                    modifier = Modifier.clip(RoundedCornerShape(20.dp,20.dp,20.dp,20.dp,))
                ) {
                    tabRowItems.forEachIndexed { index, item ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                            icon = {
                                Icon(painterResource(item.icon),contentDescription = "" )
                            },
                            text = {
                                Text(
                                    text = item.title,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        )
                    }
                }
                HorizontalPager(
                    count = tabRowItems.size,
                    state = pagerState,

                ) {
                    tabRowItems[it].screen()
                }
            }
        }
    }

    @Composable
    fun HeroPickerTopBar(){
        LazyRow(){
            if(pickedHeroes.isNotEmpty())
                itemsIndexed(pickedHeroes){id,item ->
                    HeroCard(item, false)
                    Spacer(Modifier.width(10.dp))
                }
        }
    }

    @Composable
    fun HeroCard(hero:Hero, isSelected:Boolean){
        Card(Modifier.fillMaxWidth(0.2f)) {
                Column(verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = hero.portrait as String,
                        contentDescription = hero.name,
                        modifier = Modifier.border(
                            width = 4.dp,
                            color = Grey,
                            shape = RoundedCornerShape(50.dp)
                        ).clip(RoundedCornerShape(50.dp,50.dp,50.dp,50.dp,)),
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

    }

    @Composable
    fun TabScreen(
        text: String,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.body1,
            )
        }
    }


}