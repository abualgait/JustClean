/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.network.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

interface ApiErrorContract {
    val status: Boolean
    val message: String

}

@JsonClass(generateAdapter = true)
data class ApiError(
        @Json(name = "code")
        val code: Int,
        @Json(name = "type")
        val type: String,
        @Json(name = "info")
        val info: String
)
