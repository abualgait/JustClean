/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.datasource.view


import androidx.lifecycle.Observer
import com.justclean.app.datasource.BaseUnitTest
import com.justclean.app.shared.data.model.Resource
import com.justclean.app.shared.data.model.posts.PostsResponseItem
import com.justclean.app.shared.network.ApiRepository
import com.justclean.app.ui.navhostactivity.post.PostFragVm
import io.reactivex.Observable
import org.junit.Test
import org.koin.core.inject
import org.mockito.Mock
import org.mockito.Mockito

class PostsViewModelTest : BaseUnitTest() {
    val repository by inject<ApiRepository>()
    private val postViewModel: PostFragVm by inject()

    @Mock
    lateinit var listObserver: Observer<Resource<List<PostsResponseItem>>>


    @Test
    fun testGetPosts() {
        repository.getPosts().blockingFirst()

        postViewModel.response.observeForever(listObserver)
        postViewModel.getPosts()
        Mockito.verify(listObserver).onChanged(Resource.success(null))

        val value = postViewModel.response.value ?: error("No value for view myModel")
        //produce me an error -->  Method getMainLooper in android.os.Looper not mocked
        Mockito.verify(listObserver).onChanged(Resource.success(value.data))

    }

    @Test
    fun testGetPostsFaild() {
        postViewModel.response.observeForever(listObserver)
        postViewModel.getPosts()

        Mockito.verify(listObserver).onChanged(Resource.success(null))


        val error = IllegalStateException("Got an error !")
        Mockito.`when`(repository.getPosts()).thenReturn(Observable.error(error))

        Mockito.verify(listObserver).onChanged(Resource.error(error.message, null))


    }
}