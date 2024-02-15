package com.bintaaaa.features.favorite

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform