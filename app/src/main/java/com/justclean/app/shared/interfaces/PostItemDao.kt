/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.interfaces

import androidx.room.*
import com.justclean.app.shared.data.model.posts.PostsResponseItem

@Dao
interface PostItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: PostsResponseItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<PostsResponseItem>)
    @Update
    fun updateItem(item: PostsResponseItem)

    @Delete
    fun deleteItem(item: PostsResponseItem)

    @Query("SELECT * FROM PostsResponseItem WHERE id == :id")
    fun getItemById(id: Int):  List<PostsResponseItem>

    @Query("SELECT * FROM PostsResponseItem")
    fun getItems():  List<PostsResponseItem>

    @Query("DELETE  FROM PostsResponseItem")
    fun deleteAllItem()

}