/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.ui.navhostactivity.post


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.justclean.app.shared.data.DataManager
import com.justclean.app.shared.data.model.Resource
import com.justclean.app.shared.data.model.posts.PostsResponseItem
import com.justclean.app.shared.network.NetworkHelper
import com.justclean.app.shared.util.SingleLiveEvent
import com.justclean.app.shared.util.ext.with
import com.justclean.app.shared.util.ext.withDB
import com.justclean.app.shared.vm.BaseViewModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val postFragVmModule = module {
    viewModel { PostFragVm(get(), get()) }
}

class PostFragVm(dataManager: DataManager, networkHelper: NetworkHelper) :
    BaseViewModel(dataManager, networkHelper) {


    private val _response = SingleLiveEvent<Resource<List<PostsResponseItem>>>()
    val response: LiveData<Resource<List<PostsResponseItem>>> = _response

    private fun getPostsFromDatabase(): Observable<List<PostsResponseItem>> =
        Observable.defer { Observable.just(database.getPostDao().getItems()) }
            .withDB(scheduler)


    private fun getPostsFromApi(): Observable<List<PostsResponseItem>> =
        api.getPosts().with(scheduler)


    @SuppressLint("CheckResult")
    fun getPosts() {
        _response.value = Resource.loading(null)
        view.showLoading()
        Observable.concat(getPostsFromDatabase(), getPostsFromApi())
            .subscribe({ result ->
                _response.value = Resource.success(result)
                Observable.just(database)
                    .subscribeOn(Schedulers.io())
                    .subscribe { db ->
                        db.getPostDao().insertItems(result)
                    }
                view.hideLoading()
            }, { throwable ->
                _response.value = Resource.error(throwable.message, null)
                view.hideLoading()
            })
    }


}