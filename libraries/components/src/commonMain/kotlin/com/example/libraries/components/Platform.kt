package com.example.libraries.components

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform