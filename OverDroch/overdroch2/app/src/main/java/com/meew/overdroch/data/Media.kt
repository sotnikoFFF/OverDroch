package com.meew.overdroch.data

enum class TYPE {
    video
}

class Media (
    var type: TYPE? = null,
    var link: String? = null,
)