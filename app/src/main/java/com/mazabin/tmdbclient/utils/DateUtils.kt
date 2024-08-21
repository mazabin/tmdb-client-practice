package com.mazabin.tmdbclient.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun String.convertToLocalDateObject(): LocalDate =
    LocalDate.parse(this)

fun LocalDate.convertToLocalDateString(): String =
    this.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))