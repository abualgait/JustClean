/*
 * Created by  Muhammad Sayed  on 1/9/21 9:56 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.data.model.favourite


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.justclean.app.shared.data.model.posts.PostsResponseItem
import java.io.Serializable

@Entity
data class FavouriteItem(
    var body: String? = "",
    @PrimaryKey
    var id: Int? = 0,
    var title: String? = "",
    var userId: Int? = 0,
    var postId: Int? = -1 // use postId to determine if the item is synchronized or not with remote server (-1 -> sync)
) : Serializable {
    companion object {
        fun map(item: FavouriteItem): PostsResponseItem {
            return PostsResponseItem(
                id = item.id,
                title = item.title,
                userId = item.userId,
                body = item.body
            )
        }
    }
}