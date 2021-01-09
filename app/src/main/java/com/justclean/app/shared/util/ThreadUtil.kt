/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util

import android.os.Handler
import android.os.Looper

object ThreadUtil {

    fun runOnUiThread(runnable: () -> Unit) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(runnable)
    }
}
