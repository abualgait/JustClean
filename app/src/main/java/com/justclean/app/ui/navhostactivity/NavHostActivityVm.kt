/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.ui.navhostactivity

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.justclean.app.shared.data.DataManager
import com.justclean.app.shared.data.model.Resource
import com.justclean.app.shared.data.model.favourite.FavouriteItem
import com.justclean.app.shared.network.NetworkHelper
import com.justclean.app.shared.util.SingleLiveEvent
import com.justclean.app.shared.util.ext.withDB
import com.justclean.app.shared.vm.BaseViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val navHostActivityModule = module {
    viewModel { NavHostActivityVm(get(), get()) }
}

class NavHostActivityVm(dataManager: DataManager, networkHelper: NetworkHelper) :
    BaseViewModel(dataManager, networkHelper) {
    /**
     *   get fav Items
     * */

    private val _response = SingleLiveEvent<Resource<List<FavouriteItem>>>()
    val response: LiveData<Resource<List<FavouriteItem>>> = _response

    @SuppressLint("CheckResult")
    fun syncFavourites() {

        database.getFavDao().getItems()
            .withDB(scheduler)
            .subscribe({ result ->
                val localeFavs = result.filter {
                    //call api for adding locale post to remote posts if locale postId = -1
                    it.postId == -1
                }
                localeFavs.map {
                    favRepositoryFactory.getPostFav().addItem(it).subscribe {
                        _response.value = Resource.complete(result)
                    }
                }
                _response.value = Resource.success(result)

            }, { throwable ->
                _response.value = Resource.error(throwable.message, null)

            })
    }
}