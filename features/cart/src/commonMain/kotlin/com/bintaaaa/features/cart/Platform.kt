package com.bintaaaa.features.cart

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform