/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util

import android.text.TextUtils
import com.justclean.app.shared.util.configs.ConstValue
import java.util.regex.Pattern

fun isValidEmail(string: String): Boolean {
    val pattern = Pattern.compile(ConstValue.EMAIL_PATTERN)
    val matcher = pattern.matcher(string)
    return matcher.matches()
}

fun isNullOrEmpty(string: String): Boolean = TextUtils.isEmpty(string)


fun isNumeric(string: String): Boolean = TextUtils.isDigitsOnly(string)


fun isPasswordLengthLessThanSix(string: String): Boolean = string.length < 6

fun isPhoneNumber(string: String): Boolean = string.length == 11

fun isNameLengthGreaterThanThree(string: String): Boolean = string.length > 3
