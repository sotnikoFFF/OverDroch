package com.meew.overdroch.data

interface OverFastDAO {
    fun getAllHeroes():List<Hero>?
    fun getHeroData(heroKey: HeroKey): Hero
}