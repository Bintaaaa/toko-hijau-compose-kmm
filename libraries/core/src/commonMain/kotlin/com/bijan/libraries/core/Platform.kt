package com.bijan.libraries.core

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform