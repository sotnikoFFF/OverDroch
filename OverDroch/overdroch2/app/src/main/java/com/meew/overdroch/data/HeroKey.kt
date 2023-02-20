package com.meew.overdroch.data

enum class HeroKey {
    ana, ashe, baptiste, bastion, brigitte, cassidy, dva, doomfist, echo, genji, hanzo, junkerQueen("junker-queen"), junkrat, kiriko, lucio, mei, mercy, moira, orisa, pharah, ramattra, reaper, reinhardt, roadhog, sigma, sojourn, soldier(
        "soldier-76"
    ),
    sombra, symmetra, torbjorn, tracer, widowmaker, winston, ball("wrecking-ball"), zarya, zenyatta;

    constructor(s: String) {}
    constructor() {}
}