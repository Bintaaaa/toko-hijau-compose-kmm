package com.example.features.home

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform