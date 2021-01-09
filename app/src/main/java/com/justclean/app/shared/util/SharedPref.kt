/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util

import android.content.SharedPreferences


enum class SPKey {
    TOKEN,
    PUSH_NOTIFICATION_TOKEN,
    REFRESH_TOKEN,
    LANG,
    PUSH_NOTIFICATION,
    IS_FIRST_TIME,
    USER,
    MY_AREA,
    REGISTER_OTP,
    SESSION,
    LAT, LON,

}

class SharedPref(pref: SharedPreferences) : AbsSharedPref(pref) {
    var access_token: String
        get() = getString(SPKey.TOKEN, "")
        set(value) {
            putString(value, SPKey.TOKEN)
        }

    var push_notification_token: String
        get() = getString(SPKey.PUSH_NOTIFICATION_TOKEN, "")
        set(value) {
            putString(value, SPKey.PUSH_NOTIFICATION_TOKEN)
        }

    var refreshToken: String
        get() = getString(SPKey.REFRESH_TOKEN, "")
        set(value) {
            putString(value, SPKey.REFRESH_TOKEN)
        }

    var lang: String
        get() = getString(SPKey.LANG, "en")
        set(value) {
            putString(value, SPKey.LANG)
        }

    var pushNotification: Boolean
        get() = getBoolean(SPKey.PUSH_NOTIFICATION, true)
        set(value) {
            putBoolean(value, SPKey.PUSH_NOTIFICATION)
        }

    var isFirstTime: Boolean
        get() = getBoolean(SPKey.IS_FIRST_TIME, true)
        set(value) {
            putBoolean(value, SPKey.IS_FIRST_TIME)
        }


    var longitude: String
        get() = getString(SPKey.LON, "0.0")
        set(value) {
            putString(value, SPKey.LON)
        }

    var latitude: String
        get() = getString(SPKey.LAT, "0.0")
        set(value) {
            putString(value, SPKey.LAT)
        }

    fun isAuthenticated(): Boolean = access_token.isNotEmpty()
    fun isFreshLaunch(): Boolean = push_notification_token.isNotEmpty()

  /*  fun getSessionUser(): SignInResponse {
        val gson = Gson()
        val json = getString(SPKey.USER, "")
        return if (!json.equals("")) {
            val obj = gson.fromJson<SignInResponse>(json, SignInResponse::class.java)
            obj
        } else {
            SignInResponse()
        }

    }

    fun setSessionUser(value: SignInResponse) {
        val gson = Gson()
        val json = gson.toJson(value)
        putString(json, SPKey.USER)

    }*/




    var session: String
        get() = getString(SPKey.SESSION, "")
        set(value) {
            putString(value, SPKey.SESSION)
        }


}

abstract class AbsSharedPref(private val pref: SharedPreferences) {
    private val editor: SharedPreferences.Editor by lazy {
        pref.edit()
    }

    fun putString(value: String?, key: SPKey) {
        editor.putString(key.name, value)
        editor.apply()
    }



    fun putInt(value: Int, key: SPKey) {
        editor.putInt(key.name, value)
        editor.apply()
    }

    fun putLong(value: Long, key: SPKey) {
        editor.putLong(key.name, value)
        editor.apply()
    }

    fun putFloat(value: Float, key: SPKey) {
        editor.putFloat(key.name, value)
        editor.apply()
    }

    fun putBoolean(value: Boolean, key: SPKey) {
        editor.putBoolean(key.name, value)
        editor.apply()
    }

    fun getString(key: SPKey, def: String): String {
        return pref.getString(key.name, def)
    }

    fun getInt(key: SPKey, def: Int): Int {
        return pref.getInt(key.name, def)
    }

    fun getLong(key: SPKey, def: Long): Long {
        return pref.getLong(key.name, def)
    }

    fun getFloat(key: SPKey, def: Float): Float {
        return pref.getFloat(key.name, def)
    }

    fun getBoolean(key: SPKey, def: Boolean): Boolean {
        return pref.getBoolean(key.name, def)
    }

    fun clear() {
        editor.clear()
        editor.apply()
    }
}

