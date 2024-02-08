package com.bijan.libraries.core.local

import androidx.compose.runtime.compositionLocalOf
import com.russhwolf.settings.Settings

class ValueDataSources {
    private val settings = Settings()

    fun setString(key: String, value: String) {
        settings.putString(key = key, value = value)
    }

    fun getString(key: String): String {
        return settings.getString(key, "")
    }

    fun removeValue(key: String){
        return settings.remove(key)
    }
}

val LocalValueDataSources = compositionLocalOf<ValueDataSources> { error("value data sources not provided") }