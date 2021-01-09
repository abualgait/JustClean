/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util


import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
class LanguagePrfs(context: Activity, isEnglish: Boolean, var sharedPreferences: SharedPref) {

    private var context: Activity? = context

    init {
        if (isEnglish) {
            saveLanguage("en")
            initRTL("en")
        } else {
            saveLanguage("ar")
            initRTL("ar")
        }

    }

    private fun saveLanguage(language: String) {
        try {
            sharedPreferences.lang = language
        } catch (exc: Exception) {

        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun initRTL(lang: String) {
        /*if (lang.equals("en", ignoreCase = true)) {

            val resources = context!!.applicationContext.resources
            // work for > 21
            val languageToLoad = "en"
            val locale = Locale(languageToLoad)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            context!!.resources.updateConfiguration(config, context!!.resources.displayMetrics)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                config.setLayoutDirection(locale)
                resources.updateConfiguration(config, null)

            }

        } else {

            val resources = context!!.applicationContext.resources
            val languageToLoad = "ar"
            val locale = Locale(languageToLoad)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            context!!.resources.updateConfiguration(config, context!!.resources.displayMetrics)

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                config.setLayoutDirection(locale)
                resources.updateConfiguration(config, null)
            }
        }
*/
        val locale = Locale(lang)
        val config =
            Configuration(context!!.resources.configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)

        context!!.baseContext.resources.updateConfiguration(
            config,
            context!!.baseContext.resources.displayMetrics
        )
    }


}