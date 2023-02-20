package com.meew.overdroch.data

import android.graphics.Bitmap
import com.meew.overdroch.utils.RestUtils.downloadImage

data class Hero(
        var key: HeroKey?,
        var name: String = "sample",
        var portrait: Any?,
        var role: Role?,
        var location: String = "",
        var abilities: List<Ability>? = emptyList(),
        var story: Story? = null
        ) {
//        constructor(
//        key: HeroKey,
//        name: String,
//        portraitUrl: String,
//        role: Role
//        ) : this(
//        key = key,
//        name = name,
//        portrait = downloadImage(portraitUrl),
//        role = role
//        )
        }