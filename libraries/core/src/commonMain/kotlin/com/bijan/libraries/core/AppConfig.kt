package com.bijan.libraries.core

import androidx.compose.runtime.compositionLocalOf

interface AppConfig {

    val baseUrl: String

}

val LocalAppConfig = compositionLocalOf<AppConfig> { error("AppConfig not provided") }