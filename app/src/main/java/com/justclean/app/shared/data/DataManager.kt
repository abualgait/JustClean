/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.data


import com.justclean.app.shared.databases.DBRepository
import com.justclean.app.shared.factory.FavRepositoryFactory
import com.justclean.app.shared.network.ApiRepository
import com.justclean.app.shared.rx.SchedulerProvider
import com.justclean.app.shared.util.SharedPref

open class DataManager(
    val pref: SharedPref,
    val api: ApiRepository,
    val database: DBRepository,
    val favRepositoryFactory: FavRepositoryFactory,
    val schedulerProvider: SchedulerProvider
)
