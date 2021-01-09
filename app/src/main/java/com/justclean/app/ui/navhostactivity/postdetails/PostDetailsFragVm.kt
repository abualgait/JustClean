/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.ui.navhostactivity.postdetails


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.justclean.app.shared.data.DataManager
import com.justclean.app.shared.data.model.Resource
import com.justclean.app.shared.data.model.favourite.FavouriteItem
import com.justclean.app.shared.data.model.posts.PostsResponseItem
import com.justclean.app.shared.data.model.posts.comments.CommentsResponseItem
import com.justclean.app.shared.network.NetworkHelper
import com.justclean.app.shared.util.ext.with
import com.justclean.app.shared.util.ext.withDB
import com.justclean.app.shared.vm.BaseViewModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val postDetailsFragVmModule = module {
    viewModel { PostDetailsFragVm(get(), get()) }
}

class PostDetailsFragVm(dataManager: DataManager, networkHelper: NetworkHelper) :
    BaseViewModel(dataManager, networkHelper) {

    private val _data = MutableLiveData<PostsResponseItem>()
    val data: LiveData<PostsResponseItem> = _data


    fun setData(result: PostsResponseItem?) {
        _data.value = result
    }


    private val _response = MutableLiveData<Resource<List<CommentsResponseItem>>>()
    val response: LiveData<Resource<List<CommentsResponseItem>>> = _response

    private fun getCommentsFromDatabase(postId: Int): Observable<List<CommentsResponseItem>> =
        Observable.defer { Observable.just(database.getCommentsDao().getItemsById(postId)) }
            .withDB(scheduler)


    private fun getCommentsFromApi(postId: Int): Observable<List<CommentsResponseItem>> =
        api.getCommentsByPostId(postId).with(scheduler)

    @SuppressLint("CheckResult")
    fun getCommentsByPostId(postId: Int) {
        _response.value = Resource.loading(null)

        Observable.concat(getCommentsFromDatabase(postId), getCommentsFromApi(postId))
            .with(scheduler)
            .subscribe({ result ->
                _response.value = Resource.success(result)
                Observable.just(database)
                    .subscribeOn(Schedulers.io())
                    .subscribe { db ->
                        db.getCommentsDao().insertItems(result)
                    }

            }, { throwable ->
                _response.value = Resource.error(throwable.message, null)

            })
    }

    /**
     * adding to favourite remote-then-locale
     */
    @SuppressLint("CheckResult")
    fun addPostToFav(post: FavouriteItem) {
        favRepositoryFactory.getPostFav().addItem(post).subscribe {
            _response.value = Resource.complete(null)
        }
        /*database.getFavDao().insertItem(post).withDB(scheduler).subscribe {
            _response.value = Resource.complete(null)
        }*/

    }

}