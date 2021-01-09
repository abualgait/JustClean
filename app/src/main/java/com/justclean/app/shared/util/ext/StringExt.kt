/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util.ext

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.widget.TextView
import java.util.regex.Pattern


fun String.withTitle(title: String): String {
    return "$title: $this"
}

fun String?.withTitleRes(title: Int, context: Context): String {
    return "${context.getString(title)}: $this"
}

fun String.withSuffix(suffix: String): String {
    return "$this $suffix"
}

fun String.withSuffixRes(suffix: Int, context: Context): String {
    return "$this ${context.getString(suffix)}"
}

fun String.withPrefix(suffix: String): String {
    return "$suffix $this"
}

fun String.withPrefixRes(suffix: Int, context: Context): String {
    return "${context.getString(suffix)} $this"
}

fun String.getYoutubeId(): String? {
    val pattern = "https?://(?:[0-9A-Z-]+\\.)?(?:youtu\\.be/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|</a>))[?=&+%\\w]*"

    val compiledPattern = Pattern.compile(pattern,
        Pattern.CASE_INSENSITIVE)
    val matcher = compiledPattern.matcher(this)
    return if (matcher.find()) {
        matcher.group(1)
    } else null
}

fun TextView.html(text: String) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text =
            if (!TextUtils.isEmpty(text)) Html.fromHtml(
                text,
                Html.FROM_HTML_MODE_COMPACT
            ) else ""

    } else {
        this.text =
            if (!TextUtils.isEmpty(text)) Html.fromHtml(
                text
            ) else ""
    }
}


fun String?.getPlainText(): Spanned? {
    if (this.isNullOrEmpty()) return null
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(this)
    }

}
