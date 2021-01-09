/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.interfaces

import androidx.room.*
import com.justclean.app.shared.data.model.posts.comments.CommentsResponseItem

@Dao
interface CommentItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: CommentsResponseItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<CommentsResponseItem>)
    @Update
    fun updateItem(item: CommentsResponseItem)

    @Delete
    fun deleteItem(item: CommentsResponseItem)

    @Query("SELECT * FROM CommentsResponseItem WHERE postId == :id")
    fun getItemsById(id: Int):  List<CommentsResponseItem>

    @Query("SELECT * FROM CommentsResponseItem")
    fun getItems():  List<CommentsResponseItem>

    @Query("DELETE  FROM CommentsResponseItem")
    fun deleteAllItem()
}