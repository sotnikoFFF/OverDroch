package com.meew.overdroch.data.maps

data class GameMap(
    var name:String,
    var screenshot:String?,
    var gamemodes:List<String>?,
    var location:String?,
    var country:String?,
)
