package com.example.libraries.components.utils

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter

actual  val Double.toRupiah: String get() {
    val numberFormat = NSNumberFormatter()
    numberFormat.locale = NSLocale("id_ID")
    numberFormat.numberStyle = 2u
    return  numberFormat.stringFromNumber(NSNumber(this)).orEmpty()
}