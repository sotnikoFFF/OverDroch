package com.meew.overdroch.ui.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meew.overdroch.data.hero.Hero
import com.meew.overdroch.ui.screens.HeroItem

@Composable
fun HeroGrid(heroes: MutableList<Hero?>?, onHeroSelected:(Hero) -> Unit){
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(0.dp,10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.padding(0.dp)
    ) {
        itemsIndexed(heroes!!) { id, a ->
            if (a != null) {
                HeroItem(hero = a,
                    isSelected = false,
                    onClick = {
                        onHeroSelected(a)
                    })
            }
        }
    }
}