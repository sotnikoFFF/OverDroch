package com.meew.overdroch.data.hero

data class Story (
    var summary: String? = null,
    var media: Media? = null,
    var chapters: List<Chapters>? = null
)