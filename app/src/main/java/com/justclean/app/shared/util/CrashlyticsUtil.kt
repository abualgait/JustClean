/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util


object CrashlyticsUtil {

    fun logAndPrint(throwable: Throwable) {
        // In production we must uncomment
        throwable.printStackTrace()
        //FirebaseCrashlytics.getInstance().recordException(throwable)
    }

    fun logAndPrint(msg: String) {
        // In production we must uncomment
        //FirebaseCrashlytics.getInstance().recordException(Throwable(msg))
    }
}

fun Exception.reportAndPrint() {
    CrashlyticsUtil.logAndPrint(this)
}