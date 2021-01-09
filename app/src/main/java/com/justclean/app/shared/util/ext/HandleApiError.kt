/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util.ext

import android.app.Activity
import android.content.Context
import android.util.Log
import org.json.JSONObject
import retrofit2.HttpException
import com.justclean.app.shared.util.FlashbarUtil
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException


fun Throwable.showError(context: Context): Boolean {
    when (this) {
        is HttpException -> {
            Log.e("error", this.toString())
            checkError(this, context)
        }
        is TimeoutException -> FlashbarUtil.show(
            "context.getString(R.string.timeout)",
            activity = context as Activity
        )
        is ConnectException -> FlashbarUtil.show(
            "context.getString(R.string.device_is_offline)",
            activity = context as Activity
        )
        is SocketTimeoutException -> FlashbarUtil.show(
            "SocketTimeoutException",
            activity = context as Activity
        )
        else -> {
            Log.e("error", this.toString())
            FlashbarUtil.show(
                "context.getString(R.string.unexcepected_error)",
                activity = context as Activity
            )
        }
    }
    return false
}

private fun checkError(errorException: HttpException?, context: Context) {

    val errorBody = errorException?.response()?.errorBody()?.string()
    val mainObject = JSONObject(errorBody)
    if (mainObject.has("message")) {
        FlashbarUtil.show(
            mainObject.getString("message"),
            activity = context as Activity
        )
    } else {

        FlashbarUtil.show(
            "context.getString(R.string.unexcepected_error)",
            activity = context as Activity
        )
    }

}