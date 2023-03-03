package com.meew.overdroch.ui.data

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
// for a 'val' variable
import androidx.compose.runtime.getValue

// for a `var` variable also add
import androidx.compose.runtime.setValue

// or just
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.meew.overdroch.data.hero.Hero
import com.meew.overdroch.heroPickerService
import com.meew.overdroch.ui.screens.EmptyHeroItem
import com.meew.overdroch.ui.screens.HeroItem

@Composable
fun HeroGrid(heroes: MutableList<Hero?>?, onHeroSelected:(Hero) -> Unit) {
        var selectedIndex by remember { mutableStateOf(0) }
    val   shape=RoundedCornerShape(10.dp)
    val modifier = Modifier.shadow(10.dp).clip(shape)
        .fillMaxWidth(0.1f).background(Color.White)
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(0.dp, 10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(0.dp)
        ) {
            itemsIndexed(heroes!!) { id, a ->
                if (a != null) {
                    HeroItem(
                        hero = a,
                        isSelected = heroPickerService!!.suggestedHeroes.contains(a),
                        onClick = {
                            selectedIndex=id
                            onHeroSelected(a)
                        },
                        modifier = modifier,
                        shape = shape
                    )
                }
            }
        }
}



@Composable
fun HeroRow(heroes: MutableList<Hero?>?, onHeroSelected:(Int) -> Unit, backgroundColor: Color) {
    var selectedItem by remember { mutableStateOf(0) }
    val modifier = Modifier
    var shape = RoundedCornerShape(10.dp)
    var color = Color.White

    LazyRow(
        contentPadding = PaddingValues(15.dp, 10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.padding(0.dp, 0.dp).background(backgroundColor.copy(0.3f)).clip(shape)
    ) {

        itemsIndexed(heroes!!) { id, a ->
            if(id==selectedItem){
                color = Color.Gray
                shape=RoundedCornerShape(10.dp)
                modifier.shadow(10.dp).clip(shape)
                    .fillMaxWidth(0.1f).background(color)

            }else{
                color = Color.White
                shape=RoundedCornerShape(10.dp)
                modifier.shadow(4.dp).clip(shape)
                    .fillMaxWidth(0.2f).background(color)

            }
            if (a != null) {
                HeroItem(hero = a,
                    isSelected =selectedItem== id,
                    onClick = {
                        selectedItem= id;
                        onHeroSelected(id)
                    }, modifier,shape)
            }else{
                EmptyHeroItem(
                    onClick = {
                        selectedItem= id;
                        onHeroSelected(id)
                    },
                    isSelected = selectedItem== id,
                    modifier,shape)
            }

        }
    }
}

