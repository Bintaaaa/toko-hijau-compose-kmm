package com.bijan.apis.product

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform