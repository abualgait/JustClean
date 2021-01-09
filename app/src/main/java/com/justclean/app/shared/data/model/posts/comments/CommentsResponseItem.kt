/*
 * Created by  Muhammad Sayed  on 1/9/21 10:07 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.data.model.posts.comments


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity
data class CommentsResponseItem(
    @Json(name = "body")
    var body: String? = "",
    @Json(name = "email")
    var email: String? = "",
    @PrimaryKey
    @Json(name = "id")
    var id: Int? = 0,
    @Json(name = "name")
    var name: String? = "",
    @Json(name = "postId")
    var postId: Int? = 0
):Serializable