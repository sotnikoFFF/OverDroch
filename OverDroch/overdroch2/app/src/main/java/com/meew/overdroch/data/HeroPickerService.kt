package com.meew.overdroch.data

import androidx.compose.runtime.mutableStateListOf
import com.meew.overdroch.data.hero.Hero

class HeroPickerService {

    var suggestedHeroes: MutableList<Hero> = mutableStateListOf()
    var rowOfOpponents: MutableList<Hero?> = mutableStateListOf(null, null, null, null, null)
    var rowOfEnemies: MutableList<Hero?> = mutableStateListOf(null, null, null, null, null)

    fun highlightHeroesByEnemy() {
        suggestedHeroes.clear()
        rowOfEnemies.forEachIndexed {id, h ->
            if(h!=null) OverFastService.heroes!!.forEach { hero: Hero ->
                if(HeroConnection(hero,h).isOverLap)
                        suggestedHeroes.add(hero)
            }
        }
    }
}



