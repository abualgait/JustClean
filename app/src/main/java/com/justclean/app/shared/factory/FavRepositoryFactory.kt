/*
 * Created by  Muhammad Sayed  on 1/9/21 5:53 PM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.factory

import com.justclean.app.shared.network.NetworkHelper
import com.justclean.app.shared.rx.SchedulerProvider

class FavRepositoryFactory(
    private val localFavRepository: LocaleFavRepository,
    private val remoteFavRepository: RemoteFavRepository,
    private val networkHelper: NetworkHelper,
    private val schedulerProvider: SchedulerProvider
) {

    fun getPostFav(): FavRepository {
        return if (networkHelper.isNetworkConnected()) {
            remoteFavRepository
        } else {
            localFavRepository
        }
    }
}