/*
 * Created by  Muhammad Sayed  on 1/9/21 5:58 PM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.factory

import com.justclean.app.shared.data.model.favourite.FavouriteItem
import com.justclean.app.shared.databases.DBRepository
import com.justclean.app.shared.interfaces.FavouriteItemDao
import com.justclean.app.shared.rx.SchedulerProvider
import com.justclean.app.shared.util.ext.withDB
import io.reactivex.Completable
import io.reactivex.Observable

class LocaleFavRepository(
    private val db: DBRepository,
    private val schedulerProvider: SchedulerProvider
) : FavRepository {
    override fun addItem(item: FavouriteItem): Completable {
        return db.getFavDao().insertItem(item).withDB(schedulerProvider)
    }

    override fun deleteItem(item: FavouriteItem): Completable {
        return db.getFavDao().deleteItem(item).withDB(schedulerProvider)
    }

    override fun loadItems(): Observable<List<FavouriteItem>> {
        return db.getFavDao().getItems().withDB(schedulerProvider)
    }

}