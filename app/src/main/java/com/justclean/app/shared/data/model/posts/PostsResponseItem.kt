/*
 * Created by  Muhammad Sayed  on 1/9/21 9:56 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.data.model.posts


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.justclean.app.shared.data.model.favourite.FavouriteItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity
@JsonClass(generateAdapter = true)
data class PostsResponseItem(
    @Json(name = "body")
    var body: String? = "",
    @PrimaryKey
    @Json(name = "id")
    var id: Int? = 0,
    @Json(name = "title")
    var title: String? = "",
    @Json(name = "userId")
    var userId: Int? = 0,
    var isAddedToFav: Boolean = false
) : Serializable {
    companion object {
        fun map(item: PostsResponseItem): FavouriteItem {
            return FavouriteItem(
                id = item.id,
                title = item.title,
                userId = item.userId,
                body = item.body
            )
        }
    }
}