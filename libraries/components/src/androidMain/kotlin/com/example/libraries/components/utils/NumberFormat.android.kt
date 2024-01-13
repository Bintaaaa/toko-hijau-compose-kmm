package com.example.libraries.components.utils

import java.text.NumberFormat
import java.util.Locale

actual  val Double.toRupiah: String get() {
    val locale = Locale("id", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(locale)
    return numberFormat.format(this)
}
