package com.example.util

class Constants {
    companion object {
        const val LIMIT_KEY = "expense_key"
        const val WELCOME_KEY = "welcome"
        const val CURRENCY_KEY = "currency"
        const val EXPENSE_LIMIT_KEY = "expense_limit"
        const val LIMIT_DURATION = "limit_duration"
    }
}


fun String.removeCommasAndDecimals(): String {
    val commaIndex = this.indexOf(",")
    val decimalIndex = this.indexOf(".")
    val endIndex = if (commaIndex >= 0) {
        commaIndex
    } else if (decimalIndex >= 0) {
        decimalIndex
    } else {
        this.length
    }
    return this.substring(0, endIndex).replace(",", "").replace(".", "")
}

fun String.addCommas(): String {
    val regex = "(\\d)(?=(\\d{3})+\$)".toRegex()
    return this.replace(regex, "\$1,")
}
