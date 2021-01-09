/*
 * Created by  Muhammad Sayed  on 1/9/21 5:58 PM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.factory

import com.justclean.app.shared.data.model.favourite.FavouriteItem
import com.justclean.app.shared.databases.DBRepository
import com.justclean.app.shared.interfaces.FavouriteItemDao
import com.justclean.app.shared.network.ApiInterface
import com.justclean.app.shared.rx.SchedulerProvider
import com.justclean.app.shared.util.ext.withDB
import io.reactivex.Completable
import io.reactivex.Observable

class RemoteFavRepository(
    private val api: ApiInterface,
    private val db: DBRepository,
    private val schedulerProvider: SchedulerProvider
) : FavRepository {
    override fun addItem(item: FavouriteItem): Completable {

        /**
         * as noted in the task -> No API calls, just do the functionality of syncing the favorites to the server
         *  api.addItem()
         *  if api isSuccessful
         *  we supposed to call api endpoint for adding post to fav list
         *  then call getFavList api and inserting list response to database
         */
        item.postId = item.id
        //set to an id that server return for a fav list so that it become sync with server , if postId=-1 means that this post still not sync with server
        //and still in locale only
        return db.getFavDao().insertItem(item).withDB(schedulerProvider)
    }

    override fun deleteItem(item: FavouriteItem): Completable {
        /**
         * as noted in the task -> No API calls, just do the functionality of syncing the favorites to the server
         *  we supposed to call api endpoint for deleting post to fav list
         *  api.deletePost()
         *  if api isSuccessful
         *  then call getFavList api and inserting list response to database
         */

        return db.getFavDao().deleteItem(item).withDB(schedulerProvider)
    }

    override fun loadItems(): Observable<List<FavouriteItem>> {

        /**
         * as noted in the task -> No API calls, just do the functionality of syncing the favorites to the server
         *  api.getPosts()
         *  we supposed to call api endpoint for getFavList api
         *  if api isSuccessful
         *  then inserting list response to database
         */
        return db.getFavDao().getItems().withDB(schedulerProvider)
    }

}