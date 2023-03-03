package com.meew.overdroch.data.hero

data class Hero(
        var key: String,
        var name: String = "sample",
        var portrait: Any?,
        var role: Role?,
        var location: String = "",
        var abilities: List<Ability>?,
        var story: Story? = null
        )