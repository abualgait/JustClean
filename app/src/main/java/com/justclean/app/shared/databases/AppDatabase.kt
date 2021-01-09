/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.justclean.app.shared.data.model.favourite.FavouriteItem
import com.justclean.app.shared.data.model.posts.PostsResponseItem
import com.justclean.app.shared.data.model.posts.comments.CommentsResponseItem
import com.justclean.app.shared.interfaces.CommentItemDao
import com.justclean.app.shared.interfaces.FavouriteItemDao
import com.justclean.app.shared.interfaces.PostItemDao


@Database(
    entities = [FavouriteItem::class, CommentsResponseItem::class, PostsResponseItem::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun favItemDao(): FavouriteItemDao
    abstract fun postItemDao(): PostItemDao
    abstract fun commentsItemDao(): CommentItemDao
}