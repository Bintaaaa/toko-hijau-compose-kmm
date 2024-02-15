package com.bintaaaa.apis.authentication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform