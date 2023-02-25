package com.meew.overdroch.data.hero

enum class TYPE {
    video
}

class Media (
    var type: TYPE? = null,
    var link: String? = null,
)