/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.network

import com.justclean.app.shared.util.SharedPref
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException

class HeadersInterceptor(private val pref: SharedPref) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        try {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer ${pref.access_token}")
                .addHeader("Accept", "application/json")
                .addHeader("X-localization", pref.lang)
                .build()
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
        }
        return chain.proceed(request)
    }
}
