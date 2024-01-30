package com.bintaaaa.features.authentication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform