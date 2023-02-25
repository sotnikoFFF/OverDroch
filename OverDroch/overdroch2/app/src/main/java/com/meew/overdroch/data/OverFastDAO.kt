package com.meew.overdroch.data

import com.meew.overdroch.data.hero.Hero
import com.meew.overdroch.data.hero.HeroKey
import com.meew.overdroch.data.maps.GameMap

interface OverFastDAO {

    var maps:MutableList<GameMap>
    var heroes: MutableList<Hero>?
    fun getAllHeroes()
    fun getHeroData(heroKey: String): Hero

    fun getAllMaps()
}