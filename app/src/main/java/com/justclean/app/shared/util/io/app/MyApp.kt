/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util.io.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate

import com.justclean.app.shared.koin.KoinHelper
import com.justclean.app.shared.util.CrashlyticsUtil


class MyApp : Application() {


    companion object {


        fun applicationContext(): Context {
            return context.applicationContext
        }

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        @JvmStatic
        fun string(@StringRes res: Int): String {
            return context.getString(res)
        }

    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        try {
            KoinHelper.start(this)
            context = applicationContext

        } catch (e: Exception) {
            CrashlyticsUtil.logAndPrint(e)
        }
    }


}
