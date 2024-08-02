package com.jarroyo.feature.home.shared.ext

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char

fun LocalDateTime.format(): String = format(LocalDateTime.Format {
    dayOfMonth()
    char('/')
    monthNumber()
    char('/')
    year()
    char(' ')
    hour()
    char(':')
    minute()
    char(':')
    second()
})
