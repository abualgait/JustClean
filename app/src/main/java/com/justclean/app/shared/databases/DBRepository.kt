/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.databases

import com.justclean.app.shared.interfaces.CommentItemDao
import com.justclean.app.shared.interfaces.FavouriteItemDao
import com.justclean.app.shared.interfaces.PostItemDao


class DBRepository(private val db: AppDatabase) {

    fun getFavDao(): FavouriteItemDao {
        return db.favItemDao()
    }

    fun getPostDao(): PostItemDao {
        return db.postItemDao()
    }

    fun getCommentsDao(): CommentItemDao {
        return db.commentsItemDao()
    }



}
