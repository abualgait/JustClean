package com.justclean.app.shared.factory

import com.justclean.app.shared.data.model.favourite.FavouriteItem
import io.reactivex.Completable
import io.reactivex.Observable

interface FavRepository {
    fun addItem(item: FavouriteItem): Completable

    fun deleteItem(item: FavouriteItem): Completable

    fun loadItems(): Observable<List<FavouriteItem>>

}