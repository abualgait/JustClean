/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.interfaces

import androidx.room.*
import com.justclean.app.shared.data.model.favourite.FavouriteItem
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface FavouriteItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: FavouriteItem):Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<FavouriteItem>):Completable
    @Update
    fun updateItem(item: FavouriteItem):Completable

    @Delete
    fun deleteItem(item: FavouriteItem):Completable

    @Query("SELECT * FROM FavouriteItem WHERE id == :id")
    fun getItemById(id: Int): Observable< List<FavouriteItem>>

    @Query("SELECT * FROM FavouriteItem")
    fun getItems():  Observable<List<FavouriteItem>>

    @Query("DELETE  FROM FavouriteItem")
    fun deleteAllItem():Completable

}