/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.network.api

import com.squareup.moshi.Json


data class Meta(

    @field:Json(name = "pagination")
    val pagination: Pagination? = null
)

data class Pagination(
    @field:Json(name = "total")
    val total: Int = 0,
    @field:Json(name = "count")
    val count: Int = 0,
    @field:Json(name = "per_page")
    val per_page: Int = 0,
    @field:Json(name = "current_page")
    val current_page: Int = 0,
    @field:Json(name = "total_pages")
    val total_pages: Int = 0,
    val nextPage: Int? = null
)

