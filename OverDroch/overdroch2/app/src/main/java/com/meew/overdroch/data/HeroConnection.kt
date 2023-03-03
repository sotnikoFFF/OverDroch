package com.meew.overdroch.data

import com.meew.overdroch.data.hero.Hero

class HeroConnection(
    val hero: Hero,
    val _hero:Hero
) {

    val isOverLap:Boolean = hero.name.contains(_hero.name.get(0), true)


}