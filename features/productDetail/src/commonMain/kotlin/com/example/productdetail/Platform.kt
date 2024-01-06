package com.example.productdetail

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform