package com.justclean.app.datasource.view

import android.content.Context
import androidx.lifecycle.Observer

import com.justclean.app.datasource.BaseUnitTest
import com.justclean.app.shared.data.model.Resource
import com.justclean.app.shared.data.model.posts.comments.CommentsResponseItem
import com.justclean.app.shared.network.ApiRepository
import com.justclean.app.ui.navhostactivity.postdetails.PostDetailsFragVm
import org.junit.Test
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito

class PostDetailsViewModelTest : BaseUnitTest() {

    val repository by inject<ApiRepository>()
    private val postViewModel: PostDetailsFragVm by inject()

    @Mock
    lateinit var uiData: Observer<Resource<List<CommentsResponseItem>>>


    @Test
    fun testGetPostDetails() {
        val test =repository.getCommentsByPostId(1).test()
        val list = repository.getCommentsByPostId(1).blockingFirst()
        /*  postViewModel.response.observeForever(uiData)
          val first = list.first()
          postViewModel.getCommentsByPostId(first.id!!)*/
        test.awaitTerminalEvent()
        Mockito.verify(uiData).onChanged(Resource.success(list))


    }
}