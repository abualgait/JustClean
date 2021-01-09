/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util.ext

import java.text.SimpleDateFormat
import java.util.*

fun String.toDateTime(): String {
    return try {
        val sdf = SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH)
        val netDate = Date(this.toLong())
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}

fun String.toDate(): String {
    return try {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        val netDate = Date(this.toLong())
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}

fun String.toDateTimeUniversal(): String {
    try {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH).parse(this)
        val timestamp = date.time

        val sdf = SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH)
        val netDate = Date(timestamp)

        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}

fun String.toDateUniversal(): String {
    try {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH).parse(this)
        val timestamp = date.time
        val sdf = SimpleDateFormat("h:mm a", Locale.ENGLISH)
        val netDate = Date(timestamp)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}